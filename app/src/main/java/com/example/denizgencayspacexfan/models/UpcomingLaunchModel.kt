package com.example.denizgencayspacexfan.models

import com.google.gson.annotations.SerializedName

data class UpcomingLaunchModel (

    @SerializedName("fairings") var fairings : Fairings,
    @SerializedName("links") var links : Links,
    @SerializedName("static_fire_date_utc") var staticFireDateUtc : String,
    @SerializedName("static_fire_date_unix") var staticFireDateUnix : String,
    @SerializedName("net") var net : Boolean,
    @SerializedName("window") var window : String,
    @SerializedName("rocket") var rocket : String,
    @SerializedName("success") var success : String,
    @SerializedName("failures") var failures : List<String>,
    @SerializedName("details") var details : String,
    @SerializedName("crew") var crew : List<String>,
    @SerializedName("ships") var ships : List<String>,
    @SerializedName("capsules") var capsules : List<String>,
    @SerializedName("payloads") var payloads : List<String>,
    @SerializedName("launchpad") var launchpad : String,
    @SerializedName("flight_number") var flightNumber : Int,
    @SerializedName("name") var name : String,
    @SerializedName("date_utc") var dateUtc : String,
    @SerializedName("date_unix") var dateUnix : Int,
    @SerializedName("date_local") var dateLocal : String,
    @SerializedName("date_precision") var datePrecision : String,
    @SerializedName("upcoming") var upcoming : Boolean,
    @SerializedName("cores") var cores : List<Cores>,
    @SerializedName("auto_update") var autoUpdate : Boolean,
    @SerializedName("tbd") var tbd : Boolean,
    @SerializedName("launch_library_id") var launchLibraryId : String,
    @SerializedName("id") var id : String

)

data class Fairings (

    @SerializedName("reused") var reused : String,
    @SerializedName("recovery_attempt") var recoveryAttempt : String,
    @SerializedName("recovered") var recovered : String,
    @SerializedName("ships") var ships : List<String>

)

data class Patch (

    @SerializedName("small") var small : String,
    @SerializedName("large") var large : String

)

data class Reddit (

    @SerializedName("campaign") var campaign : String,
    @SerializedName("launch") var launch : String,
    @SerializedName("media") var media : String,
    @SerializedName("recovery") var recovery : String

)

data class Flickr (

    @SerializedName("small") var small : List<String>,
    @SerializedName("original") var original : List<String>

)

data class Links (

    @SerializedName("patch") var patch : Patch,
    @SerializedName("reddit") var reddit : Reddit,
    @SerializedName("flickr") var flickr : Flickr,
    @SerializedName("presskit") var presskit : String,
    @SerializedName("webcast") var webcast : String,
    @SerializedName("youtube_id") var youtubeId : String,
    @SerializedName("article") var article : String,
    @SerializedName("wikipedia") var wikipedia : String

)

data class Cores (

    @SerializedName("core") var core : String,
    @SerializedName("flight") var flight : Int,
    @SerializedName("gridfins") var gridfins : String,
    @SerializedName("legs") var legs : String,
    @SerializedName("reused") var reused : Boolean,
    @SerializedName("landing_attempt") var landingAttempt : String,
    @SerializedName("landing_success") var landingSuccess : String,
    @SerializedName("landing_type") var landingType : String,
    @SerializedName("landpad") var landpad : String

)