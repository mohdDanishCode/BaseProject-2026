package com.omniful.designsystem.components.countrycodepicker


import android.content.Context
import com.omniful.designsystem.R
import java.util.*

data class CountryCode(
    var countryCode: String,
    val countryPhoneCode: String = "",
    val countryName: String = "",
    val countryNameArabic: String = "",
    val phoneNoLength: Int,
    val flagResID: Int = 0,
)

fun getFlags(countryName: String): Int {
    return when (countryName) {
        "ad" -> R.drawable.flag_andorra
        "ae" -> R.drawable.flag_uae
        "af" -> R.drawable.flag_afghanistan
        "ag" -> R.drawable.flag_antigua_and_barbuda
        "ai" -> R.drawable.flag_anguilla
        "al" -> R.drawable.flag_albania
        "am" -> R.drawable.flag_armenia
        "ao" -> R.drawable.flag_angola
        "aq" -> R.drawable.flag_antarctica
        "ar" -> R.drawable.flag_argentina
        "as" -> R.drawable.flag_american_samoa
        "at" -> R.drawable.flag_austria
        "au" -> R.drawable.flag_australia
        "aw" -> R.drawable.flag_aruba
        "ax" -> R.drawable.flag_aland
        "az" -> R.drawable.flag_azerbaijan
        "ba" -> R.drawable.flag_bosnia
        "bb" -> R.drawable.flag_barbados
        "bd" -> R.drawable.flag_bangladesh
        "be" -> R.drawable.flag_belgium
        "bf" -> R.drawable.flag_burkina_faso
        "bg" -> R.drawable.flag_bulgaria
        "bh" -> R.drawable.flag_bahrain
        "bi" -> R.drawable.flag_burundi
        "bj" -> R.drawable.flag_benin
        "bl" -> R.drawable.flag_saint_barthelemy // custom
        "bm" -> R.drawable.flag_bermuda
        "bn" -> R.drawable.flag_brunei
        "bo" -> R.drawable.flag_bolivia
        "br" -> R.drawable.flag_brazil
        "bs" -> R.drawable.flag_bahamas
        "bt" -> R.drawable.flag_bhutan
        "bw" -> R.drawable.flag_botswana
        "by" -> R.drawable.flag_belarus
        "bz" -> R.drawable.flag_belize
        "ca" -> R.drawable.flag_canada
        "cc" -> R.drawable.flag_cocos // custom
        "cd" -> R.drawable.flag_democratic_republic_of_the_congo
        "cf" -> R.drawable.flag_central_african_republic
        "cg" -> R.drawable.flag_republic_of_the_congo
        "ch" -> R.drawable.flag_switzerland
        "ci" -> R.drawable.flag_cote_divoire
        "ck" -> R.drawable.flag_cook_islands
        "cl" -> R.drawable.flag_chile
        "cm" -> R.drawable.flag_cameroon
        "cn" -> R.drawable.flag_china
        "co" -> R.drawable.flag_colombia
        "cr" -> R.drawable.flag_costa_rica
        "cu" -> R.drawable.flag_cuba
        "cv" -> R.drawable.flag_cape_verde
        "cw" -> R.drawable.flag_curacao
        "cx" -> R.drawable.flag_christmas_island
        "cy" -> R.drawable.flag_cyprus
        "cz" -> R.drawable.flag_czech_republic
        "de" -> R.drawable.flag_germany
        "dj" -> R.drawable.flag_djibouti
        "dk" -> R.drawable.flag_denmark
        "dm" -> R.drawable.flag_dominica
        "do" -> R.drawable.flag_dominican_republic
        "dz" -> R.drawable.flag_algeria
        "ec" -> R.drawable.flag_ecuador
        "ee" -> R.drawable.flag_estonia
        "eg" -> R.drawable.flag_egypt
        "er" -> R.drawable.flag_eritrea
        "es" -> R.drawable.flag_spain
        "et" -> R.drawable.flag_ethiopia
        "fi" -> R.drawable.flag_finland
        "fj" -> R.drawable.flag_fiji
        "fk" -> R.drawable.flag_falkland_islands
        "fm" -> R.drawable.flag_micronesia
        "fo" -> R.drawable.flag_faroe_islands
        "fr" -> R.drawable.flag_france
        "ga" -> R.drawable.flag_gabon
        "gb" -> R.drawable.flag_united_kingdom
        "gd" -> R.drawable.flag_grenada
        "ge" -> R.drawable.flag_georgia
        "gf" -> R.drawable.flag_guyane
        "gg" -> R.drawable.flag_guernsey
        "gh" -> R.drawable.flag_ghana
        "gi" -> R.drawable.flag_gibraltar
        "gl" -> R.drawable.flag_greenland
        "gm" -> R.drawable.flag_gambia
        "gn" -> R.drawable.flag_guinea
        "gp" -> R.drawable.flag_guadeloupe
        "gq" -> R.drawable.flag_equatorial_guinea
        "gr" -> R.drawable.flag_greece
        "gt" -> R.drawable.flag_guatemala
        "gu" -> R.drawable.flag_guam
        "gw" -> R.drawable.flag_guinea_bissau
        "gy" -> R.drawable.flag_guyana
        "hk" -> R.drawable.flag_hong_kong
        "hn" -> R.drawable.flag_honduras
        "hr" -> R.drawable.flag_croatia
        "ht" -> R.drawable.flag_haiti
        "hu" -> R.drawable.flag_hungary
        "id" -> R.drawable.flag_indonesia
        "ie" -> R.drawable.flag_ireland
        "im" -> R.drawable.flag_isleof_man // custom
        "is" -> R.drawable.flag_iceland
        "in" -> R.drawable.flag_india
        "io" -> R.drawable.flag_british_indian_ocean_territory
        "iq" -> R.drawable.flag_iraq_new
        "ir" -> R.drawable.flag_iran
        "it" -> R.drawable.flag_italy
        "je" -> R.drawable.flag_jersey
        "jm" -> R.drawable.flag_jamaica
        "jo" -> R.drawable.flag_jordan
        "jp" -> R.drawable.flag_japan
        "ke" -> R.drawable.flag_kenya
        "kg" -> R.drawable.flag_kyrgyzstan
        "kh" -> R.drawable.flag_cambodia
        "ki" -> R.drawable.flag_kiribati
        "km" -> R.drawable.flag_comoros
        "kn" -> R.drawable.flag_saint_kitts_and_nevis
        "kp" -> R.drawable.flag_north_korea
        "kr" -> R.drawable.flag_south_korea
        "kw" -> R.drawable.flag_kuwait
        "ky" -> R.drawable.flag_cayman_islands
        "kz" -> R.drawable.flag_kazakhstan
        "la" -> R.drawable.flag_laos
        "lb" -> R.drawable.flag_lebanon
        "lc" -> R.drawable.flag_saint_lucia
        "li" -> R.drawable.flag_liechtenstein
        "lk" -> R.drawable.flag_sri_lanka
        "lr" -> R.drawable.flag_liberia
        "ls" -> R.drawable.flag_lesotho
        "lt" -> R.drawable.flag_lithuania
        "lu" -> R.drawable.flag_luxembourg
        "lv" -> R.drawable.flag_latvia
        "ly" -> R.drawable.flag_libya
        "ma" -> R.drawable.flag_morocco
        "mc" -> R.drawable.flag_monaco
        "md" -> R.drawable.flag_moldova
        "me" -> R.drawable.flag_of_montenegro // custom
        "mf" -> R.drawable.flag_saint_martin
        "mg" -> R.drawable.flag_madagascar
        "mh" -> R.drawable.flag_marshall_islands
        "mk" -> R.drawable.flag_macedonia
        "ml" -> R.drawable.flag_mali
        "mm" -> R.drawable.flag_myanmar
        "mn" -> R.drawable.flag_mongolia
        "mo" -> R.drawable.flag_macao
        "mp" -> R.drawable.flag_northern_mariana_islands
        "mq" -> R.drawable.flag_martinique
        "mr" -> R.drawable.flag_mauritania
        "ms" -> R.drawable.flag_montserrat
        "mt" -> R.drawable.flag_malta
        "mu" -> R.drawable.flag_mauritius
        "mv" -> R.drawable.flag_maldives
        "mw" -> R.drawable.flag_malawi
        "mx" -> R.drawable.flag_mexico
        "my" -> R.drawable.flag_malaysia
        "mz" -> R.drawable.flag_mozambique
        "na" -> R.drawable.flag_namibia
        "nc" -> R.drawable.flag_new_caledonia // custom
        "ne" -> R.drawable.flag_niger
        "nf" -> R.drawable.flag_norfolk_island
        "ng" -> R.drawable.flag_nigeria
        "ni" -> R.drawable.flag_nicaragua
        "nl" -> R.drawable.flag_netherlands
        "no" -> R.drawable.flag_norway
        "np" -> R.drawable.flag_nepal
        "nr" -> R.drawable.flag_nauru
        "nu" -> R.drawable.flag_niue
        "nz" -> R.drawable.flag_new_zealand
        "om" -> R.drawable.flag_oman
        "pa" -> R.drawable.flag_panama
        "pe" -> R.drawable.flag_peru
        "pf" -> R.drawable.flag_french_polynesia
        "pg" -> R.drawable.flag_papua_new_guinea
        "ph" -> R.drawable.flag_philippines
        "pk" -> R.drawable.flag_pakistan
        "pl" -> R.drawable.flag_poland
        "pm" -> R.drawable.flag_saint_pierre
        "pn" -> R.drawable.flag_pitcairn_islands
        "pr" -> R.drawable.flag_puerto_rico
        "ps" -> R.drawable.flag_palestine
        "pt" -> R.drawable.flag_portugal
        "pw" -> R.drawable.flag_palau
        "py" -> R.drawable.flag_paraguay
        "qa" -> R.drawable.flag_qatar
        "re" -> R.drawable.flag_martinique // no exact flag found
        "ro" -> R.drawable.flag_romania
        "rs" -> R.drawable.flag_serbia // custom
        "ru" -> R.drawable.flag_russian_federation
        "rw" -> R.drawable.flag_rwanda
        "sa" -> R.drawable.flag_saudi_arabia
        "sb" -> R.drawable.flag_soloman_islands
        "sc" -> R.drawable.flag_seychelles
        "sd" -> R.drawable.flag_sudan
        "se" -> R.drawable.flag_sweden
        "sg" -> R.drawable.flag_singapore
        "sh" -> R.drawable.flag_saint_helena // custom
        "si" -> R.drawable.flag_slovenia
        "sk" -> R.drawable.flag_slovakia
        "sl" -> R.drawable.flag_sierra_leone
        "sm" -> R.drawable.flag_san_marino
        "sn" -> R.drawable.flag_senegal
        "so" -> R.drawable.flag_somalia
        "sr" -> R.drawable.flag_suriname
        "ss" -> R.drawable.flag_south_sudan
        "st" -> R.drawable.flag_sao_tome_and_principe
        "sv" -> R.drawable.flag_el_salvador
        "sx" -> R.drawable.flag_sint_maarten
        "sy" -> R.drawable.flag_syria
        "sz" -> R.drawable.flag_swaziland
        "tc" -> R.drawable.flag_turks_and_caicos_islands
        "td" -> R.drawable.flag_chad
        "tg" -> R.drawable.flag_togo
        "th" -> R.drawable.flag_thailand
        "tj" -> R.drawable.flag_tajikistan
        "tk" -> R.drawable.flag_tokelau // custom
        "tl" -> R.drawable.flag_timor_leste
        "tm" -> R.drawable.flag_turkmenistan
        "tn" -> R.drawable.flag_tunisia
        "to" -> R.drawable.flag_tonga
        "tr" -> R.drawable.flag_turkey
        "tt" -> R.drawable.flag_trinidad_and_tobago
        "tv" -> R.drawable.flag_tuvalu
        "tw" -> R.drawable.flag_taiwan
        "tz" -> R.drawable.flag_tanzania
        "ua" -> R.drawable.flag_ukraine
        "ug" -> R.drawable.flag_uganda
        "us" -> R.drawable.flag_united_states_of_america
        "uy" -> R.drawable.flag_uruguay
        "uz" -> R.drawable.flag_uzbekistan
        "va" -> R.drawable.flag_vatican_city
        "vc" -> R.drawable.flag_saint_vicent_and_the_grenadines
        "ve" -> R.drawable.flag_venezuela
        "vg" -> R.drawable.flag_british_virgin_islands
        "vi" -> R.drawable.flag_us_virgin_islands
        "vn" -> R.drawable.flag_vietnam
        "vu" -> R.drawable.flag_vanuatu
        "wf" -> R.drawable.flag_wallis_and_futuna
        "ws" -> R.drawable.flag_samoa
        "xk" -> R.drawable.flag_kosovo
        "ye" -> R.drawable.flag_yemen
        "yt" -> R.drawable.flag_martinique // no exact flag found
        "za" -> R.drawable.flag_south_africa
        "zm" -> R.drawable.flag_zambia
        "zw" -> R.drawable.flag_zimbabwe
        else -> R.drawable.flag_transparent
    }
}
fun defaultCountryCode(): CountryCode {
    return CountryCode("sa", "+966", "Saudi Arabia", "المملكة العربية السعودية", 9)
}

fun extractCountryCode(context: Context, phoneNumber: String): Pair<CountryCode, String> {
    var defaultCountry = defaultCountryCode()
    for (country in getListOfCountries()) {
        if (phoneNumber.replace("+", "").startsWith(country.countryPhoneCode.replace("+", ""))) {
            val countryCode = country.countryPhoneCode.replace("+", "")
            val remainingPhoneNumber = phoneNumber.replace("+", "").substring(countryCode.length)
            return Pair(country, remainingPhoneNumber)
        }
        if (context.resources.configuration.locale.country.lowercase() == country.countryCode) {
            defaultCountry = country
        }
    }

    return Pair(defaultCountry, phoneNumber) // Country code not found
}

fun getListOfCountries(): List<CountryCode> {
    val countries: MutableList<CountryCode> = ArrayList()
    countries.add(CountryCode("sa", "+966", "Saudi Arabia", "المملكة العربية السعودية", 9))
    countries.add(CountryCode("ad", "+376", "Andorra", "أندورا", 6))
    countries.add(CountryCode("ae", "+971", "United Arab Emirates (UAE)", "الإمارات العربية المتحدة", 9))
    countries.add(CountryCode("af", "+93", "Afghanistan", "أفغانستان", 9))
    countries.add(CountryCode("ag", "+1", "Antigua and Barbuda", "أنتيغوا وبربودا", 10))
    countries.add(CountryCode("ai", "+1", "Anguilla", "أنغويلا", 10))
    countries.add(CountryCode("al", "+355", "Albania", "ألبانيا", 9))
    countries.add(CountryCode("am", "+374", "Armenia", "أرمينيا", 8))
    countries.add(CountryCode("ao", "+244", "Angola", "أنغولا", 9))
    countries.add(CountryCode("aq", "+672", "Antarctica", "القطب الجنوبي", 6)) // No fixed phone number length
    countries.add(CountryCode("ar", "+54", "Argentina", "الأرجنتين", 13))
    countries.add(CountryCode("as", "+1", "American Samoa", "ساموا الأمريكية", 10))
    countries.add(CountryCode("at", "+43", "Austria", "النمسا", 12))
    countries.add(CountryCode("au", "+61", "Australia", "أستراليا", 9))
    countries.add(CountryCode("aw", "+297", "Aruba", "أروبا", 7))
    countries.add(CountryCode("ax", "+358", "Åland Islands", "جزر آلاند", 6))
    countries.add(CountryCode("az", "+994", "Azerbaijan", "أذربيجان", 9))
    countries.add(CountryCode("ba", "+387", "Bosnia And Herzegovina", "البوسنة والهرسك", 8))
    countries.add(CountryCode("bb", "+1", "Barbados", "بربادوس", 10))
    countries.add(CountryCode("bd", "+880", "Bangladesh", "بنغلاديش", 10))
    countries.add(CountryCode("be", "+32", "Belgium", "بلجيكا", 9))
    countries.add(CountryCode("bf", "+226", "Burkina Faso", "بوركينا فاسو", 8))
    countries.add(CountryCode("bg", "+359", "Bulgaria", "بلغاريا", 9))
    countries.add(CountryCode("bh", "+973", "Bahrain", "البحرين", 8))
    countries.add(CountryCode("bi", "+257", "Burundi", "بوروندي", 8))
    countries.add(CountryCode("bj", "+229", "Benin", "بنين", 8))
    countries.add(CountryCode("bl", "+590", "Saint Barthélemy", "سانت بارتيليمي", 12))
    countries.add(CountryCode("bm", "+1", "Bermuda", "برمودا", 10))
    countries.add(CountryCode("bn", "+673", "Brunei Darussalam", "بروناي دار السلام", 8))
    countries.add(CountryCode("bo", "+591", "Bolivia, Plurinational State Of", "بوليفيا", 8))
    countries.add(CountryCode("br", "+55", "Brazil", "البرازيل", 11))
    countries.add(CountryCode("bs", "+1", "Bahamas", "جزر البهاما", 10))
    countries.add(CountryCode("bt", "+975", "Bhutan", "بوتان", 8))
    countries.add(CountryCode("bw", "+267", "Botswana", "بوتسوانا", 8))
    countries.add(CountryCode("by", "+375", "Belarus", "بيلاروسيا", 10))
    countries.add(CountryCode("bz", "+501", "Belize", "بليز", 7))
    countries.add(CountryCode("ca", "+1", "Canada", "كندا", 10))
    countries.add(CountryCode("cc", "+61", "Cocos (Keeling) Islands", "جزر كوكوس", 9))
    countries.add(CountryCode("cd", "+243", "Congo, The Democratic Republic Of The", "جمهورية الكونغو الديمقراطية", 9))
    countries.add(CountryCode("cf", "+236", "Central African Republic", "جمهورية افريقيا الوسطى", 8))
    countries.add(CountryCode("cg", "+242", "Congo", "جمهورية الكونغو", 8))
    countries.add(CountryCode("ch", "+41", "Switzerland", "سويسرا", 9))
    countries.add(CountryCode("ci", "+225", "Côte d'Ivoire", "ساحل العاج", 8))
    countries.add(CountryCode("ck", "+682", "Cook Islands", "جزر كوك", 6))
    countries.add(CountryCode("cl", "+56", "Chile", "تشيلي", 8))
    countries.add(CountryCode("cm", "+237", "Cameroon", "الكاميرون", 8))
    countries.add(CountryCode("cn", "+86", "China", "الصين", 11))
    countries.add(CountryCode("co", "+57", "Colombia", "كولومبيا", 10))
    countries.add(CountryCode("cr", "+506", "Costa Rica", "كوستاريكا", 8))
    countries.add(CountryCode("cu", "+53", "Cuba", "كوبا", 8))
    countries.add(CountryCode("cv", "+238", "Cape Verde", "الرأس الأخضر", 7))
    countries.add(CountryCode("cw", "+599", "Curaçao", "كوراساو", 7))
    countries.add(CountryCode("cx", "+61", "Christmas Island", "جزيرة الكريسماس", 9))
    countries.add(CountryCode("cy", "+357", "Cyprus", "قبرص", 8))
    countries.add(CountryCode("cz", "+420", "Czech Republic", "الجمهورية التشيكية", 9))
    countries.add(CountryCode("de", "+49", "Germany", "ألمانيا", 12))
    countries.add(CountryCode("dj", "+253", "Djibouti", "جيبوتي", 8))
    countries.add(CountryCode("dk", "+45", "Denmark", "الدنمارك", 8))
    countries.add(CountryCode("dm", "+1", "Dominica", "دومينيكا", 10))
    countries.add(CountryCode("do", "+1", "Dominican Republic", "جمهورية الدومينيكان", 10))
    countries.add(CountryCode("dz", "+213", "Algeria", "الجزائر", 9))
    countries.add(CountryCode("ec", "+593", "Ecuador", "الإكوادور", 9))
    countries.add(CountryCode("ee", "+372", "Estonia", "إستونيا", 8))
    countries.add(CountryCode("eg", "+20", "Egypt", "مصر", 10))
    countries.add(CountryCode("eh", "+212", "Western Sahara", "الصحراء الغربية", 9))
    countries.add(CountryCode("er", "+291", "Eritrea", "إريتريا", 8))
    countries.add(CountryCode("es", "+34", "Spain", "إسبانيا", 9))
    countries.add(CountryCode("et", "+251", "Ethiopia", "إثيوبيا", 9))
    countries.add(CountryCode("fi", "+358", "Finland", "فنلندا", 10))
    countries.add(CountryCode("fj", "+679", "Fiji", "فيجي", 7))
    countries.add(CountryCode("fk", "+500", "Falkland Islands (Malvinas)", "جزر فوكلاند", 5))
    countries.add(CountryCode("fm", "+691", "Micronesia, Federated States Of", "ولايات ميكرونيزيا الموحدة", 7))
    countries.add(CountryCode("fo", "+298", "Faroe Islands", "جزر فارو", 6))
    countries.add(CountryCode("fr", "+33", "France", "فرنسا", 9))
    countries.add(CountryCode("ga", "+241", "Gabon", "الغابون", 7))
    countries.add(CountryCode("gb", "+44", "United Kingdom", "المملكة المتحدة", 10))
    countries.add(CountryCode("gd", "+1", "Grenada", "غرينادا", 10))
    countries.add(CountryCode("ge", "+995", "Georgia", "جورجيا", 9))
    countries.add(CountryCode("gf", "+594", "French Guiana", "غويانا الفرنسية", 9))
    countries.add(CountryCode("gg", "+44", "Guernsey", "غيرنزي", 10))
    countries.add(CountryCode("gh", "+233", "Ghana", "غانا", 9))
    countries.add(CountryCode("gi", "+350", "Gibraltar", "جبل طارق", 8))
    countries.add(CountryCode("gl", "+299", "Greenland", "جرينلاند", 6))
    countries.add(CountryCode("gm", "+220", "Gambia", "غامبيا", 8))
    countries.add(CountryCode("gn", "+224", "Guinea", "غينيا", 8))
    countries.add(CountryCode("gp", "+590", "Guadeloupe", "جوادلوب", 12))
    countries.add(CountryCode("gq", "+240", "Equatorial Guinea", "غينيا الاستوائية", 9))
    countries.add(CountryCode("gr", "+30", "Greece", "اليونان", 10))
    countries.add(CountryCode("gs", "+500", "South Georgia And The South Sandwich Islands", "جورجيا الجنوبية وجزر ساندويتش الجنوبية", 5))
    countries.add(CountryCode("gt", "+502", "Guatemala", "غواتيمالا", 8))
    countries.add(CountryCode("gu", "+1", "Guam", "غوام", 10))
    countries.add(CountryCode("gw", "+245", "Guinea-Bissau", "غينيا بيساو", 8))
    countries.add(CountryCode("gy", "+592", "Guyana", "غيانا", 8))
    countries.add(CountryCode("hk", "+852", "Hong Kong", "هونغ كونغ", 8))
    countries.add(CountryCode("hn", "+504", "Honduras", "هندوراس", 8))
    countries.add(CountryCode("hr", "+385", "Croatia", "كرواتيا", 9))
    countries.add(CountryCode("ht", "+509", "Haiti", "هايتي", 8))
    countries.add(CountryCode("hu", "+36", "Hungary", "هنغاريا", 9))
    countries.add(CountryCode("id", "+62", "Indonesia", "إندونيسيا", 11))
    countries.add(CountryCode("ie", "+353", "Ireland", "أيرلندا", 10))
    countries.add(CountryCode("il", "+972", "Israel", "إسرائيل", 9))
    countries.add(CountryCode("im", "+44", "Isle Of Man", "جزيرة مان", 10))
    countries.add(CountryCode("in", "+91", "India", "الهند", 10))
    countries.add(CountryCode("io", "+246", "British Indian Ocean Territory", "إقليم المحيط الهندي البريطاني", 7))
    countries.add(CountryCode("iq", "+964", "Iraq", "العراق", 10))
    countries.add(CountryCode("ir", "+98", "Iran, Islamic Republic Of", "إيران", 10))
    countries.add(CountryCode("is", "+354", "Iceland", "آيسلندا", 7))
    countries.add(CountryCode("it", "+39", "Italy", "إيطاليا", 12))
    countries.add(CountryCode("je", "+44", "Jersey", "جيرزي", 10))
    countries.add(CountryCode("jm", "+1", "Jamaica", "جامايكا", 10))
    countries.add(CountryCode("jo", "+962", "Jordan", "الأردن", 9))
    countries.add(CountryCode("jp", "+81", "Japan", "اليابان", 10))
    countries.add(CountryCode("ke", "+254", "Kenya", "كينيا", 9))
    countries.add(CountryCode("kg", "+996", "Kyrgyzstan", "قيرغيزستان", 9))
    countries.add(CountryCode("kh", "+855", "Cambodia", "كمبوديا", 8))
    countries.add(CountryCode("ki", "+686", "Kiribati", "كيريباتي", 5))
    countries.add(CountryCode("km", "+269", "Comoros", "جزر القمر", 8))
    countries.add(CountryCode("kn", "+1", "Saint Kitts And Nevis", "سانت كيتس ونيفيس", 10))
    countries.add(CountryCode("kp", "+850", "Korea, Democratic People's Republic Of", "كوريا الشمالية", 9))
    countries.add(CountryCode("kr", "+82", "Korea, Republic Of", "كوريا الجنوبية", 10))
    countries.add(CountryCode("kw", "+965", "Kuwait", "الكويت", 8))
    countries.add(CountryCode("ky", "+1", "Cayman Islands", "جزر كايمان", 10))
    countries.add(CountryCode("kz", "+7", "Kazakhstan", "كازاخستان", 10))
    countries.add(CountryCode("la", "+856", "Lao People's Democratic Republic", "لاوس", 9))
    countries.add(CountryCode("lb", "+961", "Lebanon", "لبنان", 8))
    countries.add(CountryCode("lc", "+1", "Saint Lucia", "سانت لوسيا", 10))
    countries.add(CountryCode("li", "+423", "Liechtenstein", "ليختنشتاين", 9))
    countries.add(CountryCode("lk", "+94", "Sri Lanka", "سريلانكا", 10))
    countries.add(CountryCode("lr", "+231", "Liberia", "ليبيريا", 8))
    countries.add(CountryCode("ls", "+266", "Lesotho", "ليسوتو", 8))
    countries.add(CountryCode("lt", "+370", "Lithuania", "ليتوانيا", 8))
    countries.add(CountryCode("lu", "+352", "Luxembourg", "لوكسمبورغ", 9))
    countries.add(CountryCode("lv", "+371", "Latvia", "لاتفيا", 8))
    countries.add(CountryCode("ly", "+218", "Libya", "ليبيا", 9))
    countries.add(CountryCode("ma", "+212", "Morocco", "المغرب", 9))
    countries.add(CountryCode("mc", "+377", "Monaco", "موناكو", 8))
    countries.add(CountryCode("md", "+373", "Moldova, Republic Of", "مولدوفا", 8))
    countries.add(CountryCode("me", "+382", "Montenegro", "الجبل الأسود", 8))
    countries.add(CountryCode("mf", "+590", "Saint Martin", "سانت مارتن", 12))
    countries.add(CountryCode("mg", "+261", "Madagascar", "مدغشقر", 9))
    countries.add(CountryCode("mh", "+692", "Marshall Islands", "جزر مارشال", 7))
    countries.add(CountryCode("mk", "+389", "North Macedonia", "مقدونيا الشمالية", 8))
    countries.add(CountryCode("ml", "+223", "Mali", "مالي", 8))
    countries.add(CountryCode("mm", "+95", "Myanmar", "ميانمار", 8))
    countries.add(CountryCode("mn", "+976", "Mongolia", "منغوليا", 8))
    countries.add(CountryCode("mo", "+853", "Macao", "ماكاو", 8))
    countries.add(CountryCode("mp", "+1", "Northern Mariana Islands", "جزر ماريانا الشمالية", 10))
    countries.add(CountryCode("mq", "+596", "Martinique", "مارتينيك", 12))
    countries.add(CountryCode("mr", "+222", "Mauritania", "موريتانيا", 8))
    countries.add(CountryCode("ms", "+1", "Montserrat", "مونتسرات", 10))
    countries.add(CountryCode("mt", "+356", "Malta", "مالطا", 8))
    countries.add(CountryCode("mu", "+230", "Mauritius", "موريشيوس", 7))
    countries.add(CountryCode("mv", "+960", "Maldives", "جزر المالديف", 7))
    countries.add(CountryCode("mw", "+265", "Malawi", "مالاوي", 9))
    countries.add(CountryCode("mx", "+52", "Mexico", "المكسيك", 10))
    countries.add(CountryCode("my", "+60", "Malaysia", "ماليزيا", 9))
    countries.add(CountryCode("mz", "+258", "Mozambique", "موزمبيق", 9))
    countries.add(CountryCode("na", "+264", "Namibia", "ناميبيا", 8))
    countries.add(CountryCode("nc", "+687", "New Caledonia", "كاليدونيا الجديدة", 9))
    countries.add(CountryCode("ne", "+227", "Niger", "النيجر", 8))
    countries.add(CountryCode("nf", "+672", "Norfolk Island", "جزيرة نورفولك", 7))
    countries.add(CountryCode("ng", "+234", "Nigeria", "نيجيريا", 10))
    countries.add(CountryCode("ni", "+505", "Nicaragua", "نيكاراجوا", 8))
    countries.add(CountryCode("nl", "+31", "Netherlands", "هولندا", 10))
    countries.add(CountryCode("no", "+47", "Norway", "النرويج", 8))
    countries.add(CountryCode("np", "+977", "Nepal", "نيبال", 9))
    countries.add(CountryCode("nr", "+674", "Nauru", "ناورو", 7))
    countries.add(CountryCode("nu", "+683", "Niue", "نيوي", 4))
    countries.add(CountryCode("nz", "+64", "New Zealand", "نيوزيلندا", 10))
    countries.add(CountryCode("om", "+968", "Oman", "عمان", 8))
    countries.add(CountryCode("pa", "+507", "Panama", "بنما", 8))
    countries.add(CountryCode("pe", "+51", "Peru", "بيرو", 9))
    countries.add(CountryCode("pf", "+689", "French Polynesia", "بولينيزيا الفرنسية", 8))
    countries.add(CountryCode("pg", "+675", "Papua New Guinea", "بابوا غينيا الجديدة", 8))
    countries.add(CountryCode("ph", "+63", "Philippines", "الفلبين", 10))
    countries.add(CountryCode("pk", "+92", "Pakistan", "باكستان", 10))
    countries.add(CountryCode("pl", "+48", "Poland", "بولندا", 9))
    countries.add(CountryCode("pm", "+508", "Saint Pierre And Miquelon", "سانت بيير وميكلون", 11))
    countries.add(CountryCode("pn", "+870", "Pitcairn", "بتكايرن", 4))
    countries.add(CountryCode("pr", "+1", "Puerto Rico", "بورتوريكو", 10))
    countries.add(CountryCode("ps", "+970", "Palestinian Territory, Occupied", "فلسطين", 9))
    countries.add(CountryCode("pt", "+351", "Portugal", "البرتغال", 9))
    countries.add(CountryCode("pw", "+680", "Palau", "بالاو", 7))
    countries.add(CountryCode("py", "+595", "Paraguay", "باراغواي", 9))
    countries.add(CountryCode("qa", "+974", "Qatar", "قطر", 8))
    countries.add(CountryCode("re", "+262", "Réunion", "ريونيون", 9))
    countries.add(CountryCode("ro", "+40", "Romania", "رومانيا", 9))
    countries.add(CountryCode("rs", "+381", "Serbia", "صربيا", 9))
    countries.add(CountryCode("ru", "+7", "Russian Federation", "روسيا", 10))
    countries.add(CountryCode("rw", "+250", "Rwanda", "رواندا", 9))
    countries.add(CountryCode("sa", "+966", "Saudi Arabia", "المملكة العربية السعودية", 9))
    countries.add(CountryCode("sb", "+677", "Solomon Islands", "جزر سليمان", 7))
    countries.add(CountryCode("sc", "+248", "Seychelles", "سيشل", 7))
    countries.add(CountryCode("sd", "+249", "Sudan", "السودان", 9))
    countries.add(CountryCode("se", "+46", "Sweden", "السويد", 10))
    countries.add(CountryCode("sg", "+65", "Singapore", "سنغافورة", 8))
    countries.add(CountryCode("sh", "+290", "Saint Helena, Ascension And Tristan Da Cunha", "سانت هيلانة وأسنسيون وتريستان دا كونها", 7))
    countries.add(CountryCode("si", "+386", "Slovenia", "سلوفينيا", 8))
    countries.add(CountryCode("sj", "+47", "Svalbard And Jan Mayen", "سفالبارد وجان ماين", 8))
    countries.add(CountryCode("sk", "+421", "Slovakia", "سلوفاكيا", 9))
    countries.add(CountryCode("sl", "+232", "Sierra Leone", "سيراليون", 8))
    countries.add(CountryCode("sm", "+378", "San Marino", "سان مارينو", 8))
    countries.add(CountryCode("sn", "+221", "Senegal", "السنغال", 9))
    countries.add(CountryCode("so", "+252", "Somalia", "الصومال", 8))
    countries.add(CountryCode("sr", "+597", "Suriname", "سورينام", 7))
    countries.add(CountryCode("ss", "+211", "South Sudan", "جنوب السودان", 9))
    countries.add(CountryCode("st", "+239", "Sao Tome And Principe", "ساو تومي وبرينسيبي", 7))
    countries.add(CountryCode("sv", "+503", "El Salvador", "السلفادور", 8))
    countries.add(CountryCode("sx", "+1", "Sint Maarten", "سانت مارتن", 10))
    countries.add(CountryCode("sy", "+963", "Syrian Arab Republic", "سوريا", 8))
    countries.add(CountryCode("sz", "+268", "Swaziland", "سوازيلاند", 8))
    countries.add(CountryCode("tc", "+1", "Turks And Caicos Islands", "جزر توركس وكايكوس", 10))
    countries.add(CountryCode("td", "+235", "Chad", "تشاد", 8))
    countries.add(CountryCode("tf", "+262", "French Southern Territories", "الأقاليم الجنوبية الفرنسية", 9))
    countries.add(CountryCode("tg", "+228", "Togo", "توغو", 8))
    countries.add(CountryCode("th", "+66", "Thailand", "تايلاند", 9))
    countries.add(CountryCode("tj", "+992", "Tajikistan", "طاجيكستان", 9))
    countries.add(CountryCode("tk", "+690", "Tokelau", "توكيلاو", 7))
    countries.add(CountryCode("tl", "+670", "Timor-Leste", "تيمور الشرقية", 8))
    countries.add(CountryCode("tm", "+993", "Turkmenistan", "تركمانستان", 8))
    countries.add(CountryCode("tn", "+216", "Tunisia", "تونس", 8))
    countries.add(CountryCode("to", "+676", "Tonga", "تونغا", 5))
    countries.add(CountryCode("tr", "+90", "Turkey", "تركيا", 10))
    countries.add(CountryCode("tt", "+1", "Trinidad and Tobago", "ترينيداد وتوباغو", 10))
    countries.add(CountryCode("tv", "+688", "Tuvalu", "توفالو", 6))
    countries.add(CountryCode("tw", "+886", "Taiwan, Province Of China", "تايوان", 9))
    countries.add(CountryCode("tz", "+255", "Tanzania, United Republic Of", "تنزانيا", 9))
    countries.add(CountryCode("ua", "+380", "Ukraine", "أوكرانيا", 10))
    countries.add(CountryCode("ug", "+256", "Uganda", "أوغندا", 9))
    countries.add(CountryCode("us", "+1", "United States", "الولايات المتحدة", 10))
    countries.add(CountryCode("uy", "+598", "Uruguay", "أوروغواي", 9))
    countries.add(CountryCode("uz", "+998", "Uzbekistan", "أوزبكستان", 9))
    countries.add(CountryCode("va", "+379", "Holy See (Vatican City State)", "الفاتيكان", 8))
    countries.add(CountryCode("vc", "+1", "Saint Vincent And The Grenadines", "سانت فينسنت والجرينادين", 10))
    countries.add(CountryCode("ve", "+58", "Venezuela, Bolivarian Republic Of", "فنزويلا", 10))
    countries.add(CountryCode("vg", "+1", "Virgin Islands, British", "جزر العذراء البريطانية", 10))
    countries.add(CountryCode("vi", "+1", "Virgin Islands, U.S.", "جزر العذراء الأمريكية", 10))
    countries.add(CountryCode("vn", "+84", "Viet Nam", "فيتنام", 10))
    countries.add(CountryCode("vu", "+678", "Vanuatu", "فانواتو", 7))
    countries.add(CountryCode("wf", "+681", "Wallis And Futuna", "واليس وفوتونا", 6))
    countries.add(CountryCode("ws", "+685", "Samoa", "ساموا", 7))
    countries.add(CountryCode("ye", "+967", "Yemen", "اليمن", 9))
    countries.add(CountryCode("yt", "+262", "Mayotte", "مايوت", 9))
    countries.add(CountryCode("za", "+27", "South Africa", "جنوب أفريقيا", 9))
    countries.add(CountryCode("zm", "+260", "Zambia", "زامبيا", 9))
    countries.add(CountryCode("zw", "+263", "Zimbabwe", "زيمبابوي", 10))

    return countries
}
