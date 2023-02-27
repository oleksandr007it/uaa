package com.idevhub.tapas.service.mapper;

import com.idevhub.tapas.domain.StatehoodSubject;
import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.service.dto.AdminServiceRequestFullRespDTO;
import com.idevhub.tapas.service.utils.AddressUtils;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceRequestMapper {
    public AdminServiceRequestFullRespDTO toDto(User user,
                                                StatehoodSubject subject) {
        AdminServiceRequestFullRespDTO target = new AdminServiceRequestFullRespDTO();

        target.setRNOKPP(user.getRnokpp());
        target.setPhone(user.getPhoneNumber());
        target.setEmail(user.getEmail());
        target.setEmailApproved(user.getEmailApprove());
        target.setPassport(user.getPasportSn());
        target.setPassportGivenBy(user.getPasportIssuedBy());
        target.setPassportGivenAt(user.getPasportDate());
        target.setPosition(user.getPosition());
//        target.setRegistrationAddress();

        target.setDeclarantId(user.getId());
        target.setUserType(user.getUserType());
        target.setLastName(user.getLastName());
        target.setName(user.getFirstName());
        target.setSurName(user.getMiddleName());
        target.setFullName(user.getFullName());
        target.setLangKey(user.getLangKey());
        target.setUserStatus(user.getStatus());
        target.setCreatedAt(user.getCreatedDate());
        target.setCreatedById(0L);//TODO
        target.setUpdatedAt(user.getLastModifiedDate());
        target.setUpdatedById(0L);//TODO
        target.setDeletedAt(user.getDeletedDate());
        target.setDeletedById(0L);//TODO

        if (subject != null) {
            target.setEdrpou(subject.getSubjectCode());
            target.setSubjectCode(subject.getSubjectCode());
            target.setSubjectName(subject.getSubjectName());
            target.setSubjectShortName(subject.getSubjectShortName());
            target.setHeadFullName(subject.getHeadFullName());
//            target.setLegalAddress(subject.getLegalAddress().getFullAddress());
            target.setZipCode(subject.getLegalAddress().getPostalCode());  //TODO refactor
            target.setKoatuu(AddressUtils.getKoatuu(subject.getLegalAddress()));//TODO
            target.setSubjectTelNumber(subject.getTelNumber());
            target.setSubjectEmail(subject.getEmail());
        }

        return target;
    }
}
