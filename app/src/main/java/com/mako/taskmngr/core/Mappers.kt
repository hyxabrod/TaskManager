package com.mako.taskmngr.core

interface DataEntity {
    fun toDomain(): IncomingDomainEntity
}

interface DomainEntity

interface IncomingDomainEntity : DomainEntity {
    fun toPresentation(): PresentationEntity
}

interface PresentationEntity
