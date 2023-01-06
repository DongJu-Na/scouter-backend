package com.kite.scouter.global.core;

import com.kite.scouter.global.utils.ObjectUtil;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
public abstract class BaseEntity {

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdDt;

  @LastModifiedDate
  @Column(insertable = false)
  private LocalDateTime modifiedDt;

  public LocalDateTime getCreatedDt() {
    return createdDt;
  }

  public void setCreated(final LocalDateTime createdDt) {
    this.modifiedDt = createdDt;
  }

  public LocalDateTime getModified() {
    return !ObjectUtil.isEmpty(modifiedDt)
      ? this.modifiedDt.atZone(LocaleContextHolder.getTimeZone().toZoneId()).toLocalDateTime()
      : null;
  }

  public void setModified(final LocalDateTime modifiedDt) {
    this.modifiedDt = modifiedDt;
  }

}
