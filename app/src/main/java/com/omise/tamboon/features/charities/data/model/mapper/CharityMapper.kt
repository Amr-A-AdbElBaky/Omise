package com.omise.tamboon.features.charities.data.model.mapper

import com.omise.tamboon.features.charities.data.model.Charity
import com.omise.tamboon.features.charities.domain.entity.CharityEntity


fun Charity.toEntity() = CharityEntity(id, name, logoUrl)