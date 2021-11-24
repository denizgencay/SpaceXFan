package com.example.denizgencayspacexfan.data.models
import com.google.gson.annotations.SerializedName

data class RocketModel (
    @SerializedName("height") var height : Height,
    @SerializedName("diameter") var diameter : Diameter,
    @SerializedName("mass") var mass : Mass,
    @SerializedName("payload_weights") var payloadWeights : List<PayloadWeights>,
    @SerializedName("flickr_images") var flickrImages : List<String>,
    @SerializedName("name") var name : String,
    @SerializedName("type") var type : String,
    @SerializedName("active") var active : Boolean,
    @SerializedName("stages") var stages : Int,
    @SerializedName("boosters") var boosters : Int,
    @SerializedName("cost_per_launch") var costPerLaunch : Int,
    @SerializedName("success_rate_pct") var successRatePct : Int,
    @SerializedName("first_flight") var firstFlight : String,
    @SerializedName("country") var country : String,
    @SerializedName("company") var company : String,
    @SerializedName("wikipedia") var wikipedia : String,
    @SerializedName("description") var description : String,
    @SerializedName("id") var id : String,
    var isLiked: Boolean = false
){
    fun setLikeStatus(isLiked:Boolean){
        this.isLiked = isLiked
    }
}

data class RocketId(
    var rocketId: String
)

data class Height (
    var meters : Double,
    var feet : Double
)

data class Mass (
    var kg : Int,
    var lb : Int
)

data class Diameter (
    var meters : Double,
    var feet : Double
)

data class PayloadWeights (

    var id : String,
    var name : String,
    var kg : Int,
    var lb : Int

)