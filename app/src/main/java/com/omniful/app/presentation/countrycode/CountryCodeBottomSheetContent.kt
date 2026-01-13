package com.omniful.app.presentation.countrycode

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.omniful.data.manager.language.LanguageUtil
import com.omniful.designsystem.R
import com.omniful.designsystem.components.countrycodepicker.CountryCode
import com.omniful.designsystem.components.countrycodepicker.getFlags
import com.omniful.designsystem.components.countrycodepicker.getListOfCountries
import com.omniful.designsystem.theme.Black100
import com.omniful.designsystem.theme.BodyType
import com.omniful.designsystem.theme.Grey25
import com.omniful.designsystem.theme.HeadingType
import com.omniful.designsystem.theme.LocalOMFColors
import com.omniful.designsystem.theme.LocalOMFSize
import com.omniful.designsystem.theme.LocalTypography
import com.omniful.designsystem.theme.PandaSearchInputField
import com.omniful.designsystem.theme.White100
import kotlinx.coroutines.launch

@Composable
fun CountryCodeBottomSheetContent(
    selectedCountry: CountryCode?,
    onSelectCountry: (CountryCode) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val typography = LocalTypography.current

    var countryList by remember {
        mutableStateOf(getListOfCountries())
    }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(16.dp),
    ) {
        Text(text = "Select Country code", style = typography.heading.styles[HeadingType.H06]!!.bold.copy(textAlign = TextAlign.Center))
        Spacer(modifier = Modifier.height(18.dp))

        PandaSearchInputField(modifier = Modifier) { value ->
            coroutineScope.launch {
                val searchText = value.lowercase().replace(Regex("[^\\p{L}\\p{N} ]"), "")
                if (searchText.isEmpty()) {
                    countryList = getListOfCountries()
                    return@launch
                }

                when (LanguageUtil.getSavedLanguage(context)) {
                    "en" -> {
                        countryList = getListOfCountries().filter {
                            it.countryName.lowercase().startsWith(searchText, ignoreCase = false) ||
                                    it.countryPhoneCode.replace("+", "").startsWith(searchText, ignoreCase = false) ||
                                    it.countryCode.lowercase().startsWith(searchText, ignoreCase = false)
                        }
                        return@launch
                    }
                    "ar" -> {
                        countryList = getListOfCountries().filter {
                            it.countryNameArabic.lowercase().startsWith(searchText, ignoreCase = false) ||
                                    it.countryPhoneCode.replace("+", "").startsWith(searchText, ignoreCase = false) ||
                                    it.countryCode.lowercase().startsWith(searchText, ignoreCase = false)
                        }
                        return@launch
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        if (countryList.isEmpty()) {
            PlaceHolderCountryCodeNotFound(primaryText = "Apologies, we couldn't find a country code", secondaryText = "Please double-check and try again", placeholderDrawableRef = R.drawable.search_placeholder)
        } else {
            LazyColumn {
                items(countryList.size) {
                    ItemCountryCode(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp), countryList[it], checked = selectedCountry?.countryCode == countryList[it].countryCode) {
                        onSelectCountry(it)
                    }
                }
            }
        }
    }
}

@Composable
fun ItemCountryCode(
    modifier: Modifier,
    countryCode: CountryCode,
    checked: Boolean = false,
    onClick: (CountryCode) -> Unit,
) {
    val context = LocalContext.current
    val color = LocalOMFColors.current
    val typography = LocalTypography.current

    val countryName = when (LanguageUtil.getSavedLanguage(context)) {
        "en" -> countryCode.countryName
        "ar" -> countryCode.countryNameArabic
        else -> countryCode.countryName
    }

    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable {
                onClick(countryCode)
            },
        colors = CardDefaults.cardColors(
            containerColor = if (checked) color.primary else White100,
        ),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, color = if (checked) color.primary  else Grey25),
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(color = White100)
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier
                        .width(32.dp)
                        .height(21.6.dp)
                        .border(width = 1.dp, color = Grey25),
                    painter = painterResource(
                        id = getFlags(
                            countryCode.countryCode,
                        ),
                    ),
                    contentDescription = "Flags",
                    contentScale = ContentScale.FillBounds,
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(modifier = Modifier.defaultMinSize(minWidth = 33.dp), text = countryCode.countryPhoneCode, style = typography.body.styles[BodyType.B01]!!.semiBold.copy(textAlign = TextAlign.Center), color = if (checked) color.primary else Black100)
                Spacer(modifier = Modifier.width(24.dp))
                Text(text = countryName, style = typography.body.styles[BodyType.B01]!!.medium.copy(textAlign = TextAlign.Start), color = if (checked) color.primary else Black100)
            }
            if (checked) {
                Icon(painter = painterResource(id = R.drawable.tick), contentDescription = "Checked", tint = color.primary)
            } else {
                Spacer(modifier = Modifier.width(20.dp))
            }
        }
    }
}


@Composable
fun PlaceHolderCountryCodeNotFound(
    modifier: Modifier = Modifier,
    primaryText: String,
    secondaryText: String,
    placeholderDrawableRef: Int = R.drawable.search_placeholder,
) {
    val typography = LocalTypography.current
    val size = LocalOMFSize.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Spacer(modifier = Modifier.padding(top = size.spacing.spacing6))
        Image(painter = painterResource(id = placeholderDrawableRef), contentDescription = "Place Holder")
        Spacer(modifier = Modifier.padding(top = size.spacing.spacing6))
        Text(text = primaryText, style = typography.heading.styles[HeadingType.H04]!!.bold.copy(textAlign = TextAlign.Center),)
        Spacer(modifier = Modifier.padding(top = size.spacing.spacing6))
        Text(text = secondaryText, style = typography.heading.styles[HeadingType.H06]!!.bold.copy(textAlign = TextAlign.Center),)
    }
}