package top.stores.companyappassement.models

import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull

data class CompanyDetailsPojo(@NotNull
                              @SerializedName("id")
                              var companyDetailsID: Int = 0,

                              @SerializedName("title")
                              var title: String? = "",

                              @SerializedName("body")
                              var companyBody : String? = "")
