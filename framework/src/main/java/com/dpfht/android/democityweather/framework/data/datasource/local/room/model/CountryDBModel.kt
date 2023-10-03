package com.dpfht.android.democityweather.framework.data.datasource.local.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dpfht.android.democityweather.domain.entity.CountryEntity

@Entity(tableName = "tbl_country", indices = [Index(value = ["country_code", "country_name"], unique = true)])
data class CountryDBModel(
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo("id")
  val id: Long? = null,
  @ColumnInfo(name = "country_code")
  val countryCode: String? = "",
  @ColumnInfo(name = "country_name")
  val countryName: String? = "",
)

fun CountryDBModel.toDomain(): CountryEntity {
  return CountryEntity(countryCode = this.countryCode ?: "", countryName = this.countryName ?: "")
}
