package com.idevhub.tapas.service;

import com.idevhub.tapas.domain.address.NaisAtsDenormalizedObject;
import com.idevhub.tapas.domain.address.NaisAtsObject;
import com.idevhub.tapas.domain.address.enumeration.NaisAtsDenormalizedObjectStatus;
import com.idevhub.tapas.domain.address.enumeration.NaisAtsDenormalizedObjectType;
import com.idevhub.tapas.repository.NaisAtsDenormalizedObjectRepository;
import com.idevhub.tapas.repository.NaisAtsObjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.CheckForNull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static com.idevhub.tapas.domain.address.enumeration.NaisAtsDenormalizedObjectStatus.ACTIVE;
import static com.idevhub.tapas.domain.address.enumeration.NaisAtsDenormalizedObjectStatus.INACTIVE;
import static com.idevhub.tapas.domain.address.enumeration.NaisAtsDenormalizedObjectType.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class NaisAtsObjectDenormalizationService {

    private final NaisAtsObjectRepository objectRepository;
    private final NaisAtsDenormalizedObjectRepository denormalizedObjectRepository;

    private LinkedBlockingQueue<Long> toDenormalizeObjectIds;
    private ExecutorService denormalizationExecutorService;

    public void denormalizeAtsObjectDictionary() {

        log.debug("Trying to denormalize ats object dictionary...");

        if (isDenormalizationProcessRunning()) {
            log.debug("Ats object denormalization process already running...");
            return;
        }

        initializeDenormalizationExecutorServiceIfNeeded();

        toDenormalizeObjectIds = new LinkedBlockingQueue<>(objectRepository.findAllIdsOrderedByType());

        denormalizationExecutorService.execute(new ObjectDenormalizationJob());
    }

    private boolean isDenormalizationProcessRunning() {
        return denormalizationExecutorService != null && !denormalizationExecutorService.isTerminated();
    }

    private void initializeDenormalizationExecutorServiceIfNeeded() {
        if (denormalizationExecutorService == null || denormalizationExecutorService.isTerminated())
            denormalizationExecutorService = Executors.newSingleThreadExecutor();
    }

    @CheckForNull
    public NaisAtsDenormalizedObject denormalizeObject(Long objectId) {

        NaisAtsObject objectToDenormalize = objectRepository.findById(objectId)
            .orElseThrow(() -> new EntityNotFoundException(NaisAtsObject.class, objectId));

        return denormalizeObject(objectToDenormalize);
    }

    private NaisAtsDenormalizedObject denormalizeObject(NaisAtsObject objectToDenormalize) {

        NaisAtsDenormalizedObjectType denormalizedObjectType = defineObjectType(objectToDenormalize);

        if (denormalizedObjectType.equals(NOT_PROCESSING_TYPE)) {
            return null;
        }

        NaisAtsDenormalizedObject denormalizedObject = buildDenormalizedObject(objectToDenormalize, denormalizedObjectType);
        defineObjectsOnStructuredName(objectToDenormalize, denormalizedObject);

        return denormalizedObject;
    }

    private NaisAtsDenormalizedObjectType defineObjectType(final NaisAtsObject object) {

        if (object == null) {
            return NOT_PROCESSING_TYPE;
        }

        final long typeLevel = object.getType().getLevel();
        final long typeCode = object.getType().getCode();

        if (typeLevel == 5 || (typeLevel == 4 && !hasChild(object.getId()))) {
            return LOCALITY_OBJECT;
        } else if ((typeLevel == 3 || typeLevel == 2) && typeCode == 2 && object.getParent().getType().getCode() == 1) {
            return LOCALITY_DISTRICT;
        } else if (typeLevel == 3 || ((typeLevel == 2 || typeLevel == 1) && typeCode == 1)) {
            return LOCALITY;
        } else return NOT_PROCESSING_TYPE;
    }

    private boolean hasChild(Long parentId) {
        return objectRepository.existsByParent_Id(parentId);
    }

    private NaisAtsDenormalizedObjectStatus defineObjectStatus(final NaisAtsObject object) {
        NaisAtsObject toCheck = object;
        while (toCheck != null) {
            if (!toCheck.getStatus().isActive()) {
                return INACTIVE;
            }
            toCheck = toCheck.getParent();
        }
        return ACTIVE;
    }

    @Nullable
    private NaisAtsDenormalizedObject defineObjectParent(final NaisAtsObject object) {

        if (object == null) {
            return null;
        }

        NaisAtsObject parentCandidate = null;
        NaisAtsDenormalizedObject parent = null;
        NaisAtsDenormalizedObjectType objectType = defineObjectType(object);

        if (objectType.equals(LOCALITY_DISTRICT)) {
            parentCandidate = object.getParent();
        }

        if (objectType.equals(LOCALITY_OBJECT)) {
            parentCandidate = object.getParent();
            while (!defineObjectType(parentCandidate).equals(LOCALITY)) {
                parentCandidate = parentCandidate.getParent();
            }
        }

        if (parentCandidate != null) {
            parent = buildDenormalizedObject(parentCandidate, defineObjectType(parentCandidate));
        }
        return parent;
    }

    @Nullable
    private String defineKoatuuCode(final NaisAtsObject object) {

        if (object == null) {
            return null;
        }

        String code = null;
        if (object.getKoatuuCode() != null && !object.getKoatuuCode().isBlank()) {
            code = object.getKoatuuCode();
        } else if (object.getParent() != null) {
            code = defineKoatuuCode(object.getParent());
        }
        return code;
    }

    private void defineObjectsOnStructuredName(final NaisAtsObject source, NaisAtsDenormalizedObject target) {

        if (source == null || !Objects.equals(source.getId(), target.getId())) {
            return;
        }

        NaisAtsObject region = null;
        NaisAtsObject district = null;
        NaisAtsObject locality;
        NaisAtsObject localityObject = null;
        NaisAtsObject temporary;

        if (target.getType().equals(LOCALITY_OBJECT)) {
            localityObject = source;
            temporary = source.getParent();
            while (!Objects.equals(temporary.getId(), target.getParent().getId())) {
                temporary = temporary.getParent();
            }
            locality = temporary;
        } else if (target.getType().equals(LOCALITY_DISTRICT)) {
            locality = source.getParent();
        } else if (target.getType().equals(LOCALITY)) {
            locality = source;
        } else {
            return;
        }

        temporary = locality.getParent();
        if (temporary != null) {
            if (temporary.getType().getLevel() == 2) {
                district = temporary;
                region = temporary.getParent();
            } else if (temporary.getType().getLevel() == 1) {
                region = temporary;
            }
        }

        target.setRegionName(defineObjectName(region));
        target.setDistrictName(defineObjectName(district));
        target.setLocalityName(defineObjectName(locality));
        target.setLocalityObjectName(defineObjectName(localityObject));

        NaisAtsDenormalizedObject parent = target.getParent();
        if (parent != null) {
            parent.setRegionName(target.getRegionName());
            parent.setDistrictName(target.getDistrictName());
            parent.setLocalityName(target.getLocalityName());
        }
    }

    @Nullable
    private String defineObjectName(final NaisAtsObject object) {

        if (object == null) {
            return null;
        }

        String name;
        final String baseName = object.getName();
        final long typeLevel = object.getType().getLevel();
        final long typeCode = object.getType().getCode();
        final String typeFullName = object.getType().getFullName();
        final String typeShortName = object.getType().getShortName();

        if (typeLevel == 5) {
            NaisAtsObject parent = object.getParent();
            if (parent == null) {
                return null;
            }
            if (parent.getType().getLevel() == 4) {
                if (typeCode == 6) {
                    name = String.format("%s, %s", parent.getName(), baseName);
                } else {
                    name = String.format("%s, %s %s", parent.getName(), typeShortName, baseName);
                }
            } else {
                if (typeCode == 6) {
                    name = baseName;
                } else {
                    name = String.format("%s %s", typeShortName, baseName);
                }
            }
        } else {
            if (typeLevel == 3 && typeCode == 2) {
                name = String.format("%s %s", baseName, typeFullName);
            } else if (typeLevel == 3 && (typeCode == 6 || typeCode == 8)) {
                name = String.format("%s %s", baseName, typeShortName);
            } else if (typeLevel == 1 || (typeLevel == 2 && typeCode == 2) || (typeLevel == 3 && typeCode == 7) || (typeLevel == 4 && typeCode == 3)) {
                name = baseName;
            } else {
                name = String.format("%s%s", typeShortName, baseName);
            }
        }
        return name;
    }

    private NaisAtsDenormalizedObject buildDenormalizedObject(final NaisAtsObject toDenormalize, final NaisAtsDenormalizedObjectType objectType) {
        return NaisAtsDenormalizedObject.builder()
            .id(toDenormalize.getId())
            .type(objectType)
            .parent(defineObjectParent(toDenormalize))
            .status(defineObjectStatus(toDenormalize))
            .searchName(toDenormalize.getName().toUpperCase())
            .koatuuCode(defineKoatuuCode(toDenormalize))
            .build();
    }

    private final class ObjectDenormalizationJob implements Runnable {
        @Override
        public void run() {

            log.debug("Running new object denormalization job...");

            while (!Thread.currentThread().isInterrupted()) {
                try {

                    if (toDenormalizeObjectIds.isEmpty()) {
                        log.debug("Shutting down object denormalization job...");
                        denormalizationExecutorService.shutdown();
                        if (!denormalizationExecutorService.awaitTermination(30, TimeUnit.SECONDS)) {
                            denormalizationExecutorService.shutdownNow();
                        }
                        return;
                    }

                    NaisAtsDenormalizedObject denormalizedObject = denormalizeObject(toDenormalizeObjectIds.take());
                    if (denormalizedObject != null) {
                        denormalizedObjectRepository.saveAndFlush(denormalizedObject);
                    }
                } catch (Exception e) {
                    log.error("Unable to process object denormalization job. Error: {}", e.getMessage());
                    log.error("Unable to process object denormalization job. Error: ", e);
                }
            }
        }
    }
}
