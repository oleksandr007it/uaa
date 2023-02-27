package com.idevhub.tapas.service;

import com.idevhub.tapas.UaaApp;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(classes = UaaApp.class)
@Transactional
public class StatehoodSubjectRepresentServiceIT {

//    private static final String DEFAULT_LOGIN = "johndoe";
//    private static final String DEFAULT_EMAIL = "johndoe@localhost";
//    private static final String DEFAULT_FIRSTNAME = "john";
//    private static final String DEFAULT_LASTNAME = "doe";
//    private static final String DEFAULT_LANGKEY = "dummy";
//    private static final String DEFAULT_SUBJECT_STATUS = UserStatus.ACTIVE;
//    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
//    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
//    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
//    private static final String DEFAULT_HEAD_FULL_NAME = "AAAAAAAAAA";
//    private static final String DEFAULT_TEL_NUMBER = "AAAAAAAAAA";
//    private static final Boolean DEFAULT_IS_EMAIL_APPROVED = false;
//    private static final String DEFAULT_EORI = "AAAAAAAAAA";
//    private static final Boolean DEFAULT_IS_ACTUAL_SAME_AS_LEGAL_ADDRESS = false;
//    private static final Integer STUBBED_SUBJECT_COUNT = 3;
//    private static final Integer STUBBED_REPRESENT_COUNT = 3;
//
//    @Autowired
//    private StatehoodSubjectRepresentService representService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private EntityManager em;
//
//    @SpyBean
//    private StatehoodSubjectRepresentRepository representRepository;
//
//    private User user;
//
//    private final List<StatehoodSubject> subjects = new ArrayList<>();
//
//    private StatehoodSubjectRepresent represent;
//
//    @BeforeEach
//    public void init() {
//        saveDefaultUser();
//        saveStatehoodSubjects();
//        saveStatehoodSubjectRepresents();
//
//    }
//
//    private void saveStatehoodSubjectRepresents() {
//        subjects.forEach(subj -> {
//            for (int i = 0; i < STUBBED_REPRESENT_COUNT; i++) {
//                StatehoodSubjectRepresent represent = new StatehoodSubjectRepresent()
//                    .subjectRepresentStatus(StatehoodSubjectRepresentStatus.ACTIVE)
//                    .createdAt(Instant.now())
//                    .setSubjectRepresentType(StatehoodSubjectRepresentType.OTHER_EMPLOYEE)
//                    .createdBy("system")
//                    .statehoodSubject(subj)
//                    .declarant(user)
//                    .currentContext(subj.getAccountDetailsStatus() == AccountDetailsStatus.FULL_CONFIRMED);
//
//                representRepository.save(represent);
//
//                if (subj.getAccountDetailsStatus() == AccountDetailsStatus.FULL_CONFIRMED)
//                    this.represent = represent;
//            }
//        });
//    }
//
//    private void saveStatehoodSubjects() {
//        for (int i = 0; i < STUBBED_SUBJECT_COUNT; i++) {
//            saveStatehoodSubject(i);
//        }
//    }
//
//    private void saveStatehoodSubject(int iter) {
//        StatehoodSubject subject = new StatehoodSubject()
//            .subjectStatus(DEFAULT_SUBJECT_STATUS)
//            .accountDetailsStatus(getDefaultAccountDetailsStatus(iter))
//            .createdAt(DEFAULT_CREATED_AT)
//            .updatedAt(DEFAULT_UPDATED_AT)
//            .deletedAt(DEFAULT_DELETED_AT)
//            .subjectCode(getDefaultSubjectCode(iter))
//            .subjectName(getDefaultSubjectName(iter))
//            .subjectShortName(getDefaultSubjectShortName(iter))
//            .headFullName(DEFAULT_HEAD_FULL_NAME)
//            .telNumber(DEFAULT_TEL_NUMBER)
//            .email(DEFAULT_EMAIL)
//            .isEmailApproved(DEFAULT_IS_EMAIL_APPROVED)
//            .eori(DEFAULT_EORI)
//            .isActualSameAsLegalAddress(DEFAULT_IS_ACTUAL_SAME_AS_LEGAL_ADDRESS);
//        // Add required entity
//        em.persist(user);
//        em.flush();
//        subject.setCreatedBy(user.getLogin());
//        // Add required entity
//        Kopfg kopfg = KopfgResourceIntTest.createEntity(em);
//        em.persist(kopfg);
//        em.flush();
//        subject.setKopfg(kopfg);
//        // Add required entity
//        Address legalAddress = AddressResourceIntTest.createEntity(em);
//        em.persist(legalAddress);
//        em.flush();
//        subject.setLegalAddress(legalAddress);
//        // Add required entity
//        Address actualAddress = AddressResourceIntTest.createEntity(em);
//        em.persist(actualAddress);
//        em.flush();
//        subject.setActualAddress(actualAddress);
//
//        em.persist(subject);
//        em.flush();
//
//        subjects.add(subject);
//    }
//
//    @NotNull
//    private String getDefaultSubjectShortName(int subjectIdStubbed) {
//        switch (subjectIdStubbed) {
//            case 0 :
//                return "ABC";
//            case 1 :
//                return "BBC";
//            default :
//                return "NBA";
//        }
//    }
//
//    @NotNull
//    private String getDefaultSubjectName(int subjectIdStubbed) {
//        switch (subjectIdStubbed) {
//            case 0 :
//                return "NAAAAAAAAAAME";
//            case 1 :
//                return "NBBBBBBBBBBME";
//            default :
//                return "NCCCCCCCCCCME";
//        }
//    }
//
//    @NotNull
//    private String getDefaultSubjectCode(int subjectIdStubbed) {
//        switch (subjectIdStubbed) {
//            case 0 :
//                return "AAAAAAAAAA";
//            case 1 :
//                return "BBBBBBBBBB";
//            default :
//                return "CCCCCCCCCC";
//        }
//    }
//
//    private AccountDetailsStatus getDefaultAccountDetailsStatus(int subjectIdStubbed) {
//        switch (subjectIdStubbed) {
//            case 0 :
//                return AccountDetailsStatus.FULL_NOT_CONFIRMED;
//            case 1 :
//                return AccountDetailsStatus.FULL_CONFIRMED;
//            default :
//                return AccountDetailsStatus.NOT_FULL;
//        }
//    }
//
//    private void saveDefaultUser() {
//        user = new User();
//        user.setLogin(DEFAULT_LOGIN);
//        user.setPassword(RandomStringUtils.random(60));
//        user.setActivated(true);
//        user.setEmail(DEFAULT_EMAIL);
//        user.setFirstName(DEFAULT_FIRSTNAME);
//        user.setLastName(DEFAULT_LASTNAME);
//        user.setLangKey(DEFAULT_LANGKEY);
//        user.setActivated(false);
//
//        userRepository.saveAndFlush(user);
//    }
//
//    @Test
//    @Transactional
//    public void changeContextTo() {
//
//        List<StatehoodSubjectRepresent> all = representRepository.findAll();
//
//        Optional<StatehoodSubjectRepresent> representOpt =
//            all.stream()
//                .filter(subj -> subj.getCurrentContext() != null && !subj.getCurrentContext())
//                .findFirst();
//
//        assertThat(representOpt.isPresent()).isTrue();
//
//        when(representRepository.findByDeclarant_IdAndStatehoodSubject_Id(any(), any()))
//            .thenReturn(Optional.of(representOpt.get()));
//
//        representService.changeContextTo(representOpt.get().getStatehoodSubject().getId());
//
//        assertThat(representRepository.findById(representOpt.get().getId()).get().getCurrentContext()).isEqualTo(true);
//    }
//
//    @Test
//    @Transactional
//    public void findAllRepresentersByDeclarantsCurrentContext() {
//
//        when(representRepository.findByDeclarant_IdAndAndCurrentContextTrue(any()))
//            .thenReturn(Optional.of(represent));
//
//        List<StatehoodSubjectRepresentMainInfoDTO> list = representService.findAllRepresentersByDeclarantsCurrentContext();
//
//        assertThat(STUBBED_REPRESENT_COUNT).isEqualTo(list.size());
//    }
//
//    @Test
//    @Transactional
//    public void getAllActiveByDeclarantId() {
//
//        when(representRepository.findByDeclarant_IdAndAndCurrentContextTrue(any()))
//            .thenReturn(Optional.of(represent));
//
//        List<StatehoodSubjectRepresentMainInfoDTO> list = representService.getAllActiveByDeclarantId(represent.getId());
//
//        assertThat(1).isEqualTo(list.size());
//    }
//
//    @Test
//    @Transactional
//    public void hasAccessToSubj() {
//
//        when(representRepository.findByDeclarant_IdAndAndCurrentContextTrue(any()))
//            .thenReturn(Optional.of(represent));
//
//        Boolean hasAccess = representService.hasAccessByIds(represent.getId(), represent.getStatehoodSubject().getId());
//
//        assertThat(hasAccess).isEqualTo(true);
//    }
}
