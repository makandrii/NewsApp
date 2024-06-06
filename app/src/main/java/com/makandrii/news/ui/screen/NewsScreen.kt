package com.makandrii.news.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.makandrii.news.R
import com.makandrii.news.data.model.News
import com.makandrii.news.ui.theme.NewsTheme

@Composable
fun NewsScreen(
    news: News,
    onBackButtonClicked: () -> Unit,
    onBookmarkButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopBar(
                isBookmarked = news.isBookmarked,
                onBackButtonClicked = onBackButtonClicked,
                onBookmarkButtonClicked = onBookmarkButtonClicked
            )
        }
    ) {
        Column(
            modifier = modifier.padding(it)
        ) {
            Text(
                text = news.title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            Row(
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium),
                    vertical = dimensionResource(id = R.dimen.padding_small)
                )
            ) {
                Text(
                    text = news.pubDate,
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = news.sourceUrl,
                    style = MaterialTheme.typography.titleSmall
                )
            }
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(news.imageUrl)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = news.content,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(
                    vertical = dimensionResource(id = R.dimen.padding_small),
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    isBookmarked: Boolean,
    onBackButtonClicked: () -> Unit,
    onBookmarkButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.my_news),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Normal
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = onBookmarkButtonClicked) {
                    if (isBookmarked) {
                        Icon(imageVector = Icons.Outlined.Bookmark, contentDescription = null)
                    } else {
                        Icon(imageVector = Icons.Outlined.BookmarkAdd, contentDescription = null)
                    }
                }
            }
        },
        navigationIcon = {
            IconButton(
                onClick = onBackButtonClicked,
                colors = IconButtonDefaults.outlinedIconButtonColors(
                    contentColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIosNew,
                    contentDescription = stringResource(R.string.back)
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            titleContentColor = MaterialTheme.colorScheme.secondary
        ),
        modifier = modifier
    )
}

@Preview
@Composable
fun NewsScreenPreview() {
    NewsTheme(darkTheme = true) {
        NewsScreen(
            news = News(
                id = "",
                title = "Japan eyes muscling up Australia’s rebuilding navy",
                imageUrl = "https://glavcom.ua/img/article/10009/25_main-v1715868668.jpg",
                content = "Japan is considering launching a bid to jointly develop its Mogami class frigates with Australia. If selected, the joint project would enhance bilateral defense capabilities, boost Japan’s local defense industry and counterbalance China’s rising naval power. Japan would seek to revamp its most advanced destroyer and export it to Australia if selected to participate in the program, according to multiple sources close to the government. In February, Australia announced its plan to acquire 11 general purpose frigates, citing ships from Japan, Spain, South Korea and Germany as potential candidates, the reports said. Australia is expected to release further details including required performance specifications later this year and offer each candidate country a proposal for possible joint development, the reports said. The Japan Times says the Japan Ministry of Defense (MOD) has started informal discussions with , which builds warships for the Japan Maritime Self Defense Force (JMSDF). The Japan MOD is reportedly considering developing the Mogami class frigate by adding any facilities and functions required by the Australian government. Mogami class frigates can be operated with a crew of about 90, half as many as similar conventional ships, due to the consolidation of onboard systems and other improvements. Japan’s Mogami class frigates are state of the art stealth vessels with advanced radar systems and a versatile armament suite for modern naval warfare and multi mission capabilities. that the ships represent a new generation of multi mission capable vessels designed to replace Japan’s aging Asagiri and Abukuma class destroyers. The stealthy ships are a product of Japan’s defense modernization efforts, with the MOD allocating funds in 2015 for a compact type hull warship featuring advanced radar systems. Japan plans to build 22 Mogami class frigates, with eight in the first batch, each costing approximately US\$452.7 million. The ships boast a stealth design derived from MHI’s ATD X Shinshin stealth fighter research, aiming to enhance affordability, miniaturization, automation and versatility. With a standard displacement of around 3,900 tons, the 130 meter long and 16 meter wide Mogami class is relatively small but highly capable. It features an advanced integrated combat information center (CIC), augmented reality technology and the ability to deploy various unmanned vehicles and a helicopter. Armaments include a Mark 45 naval gun, Mk 41 VLS for missiles, anti ship missiles and SeaRAM system. The sensor suite comprises multifunction radar, EO/IR sensors, AESA radar, sonar systems and a combat management system. Powered by a CODAG propulsion system with a MT30 gas turbine and MAN diesel engines, the frigate can exceed 30 knots. Japan’s export of Mogami class frigates would bolster an already budding naval partnership with Australia, security exports notes. , Peter Dan says that Japan and Australia have comparable security strategies in which they prioritize a multipolar Indo Pacific order, deterrence by denial and a regional balancing strategy centered on US allies and partners. Dan notes using identical types of ships would enhance this alignment. In terms of capability, Dan says that the frigates are designed to operate the MH 60 Seahawk helicopter, which the Royal Australian Navy (RAN) is committed to acquiring to ensure compatibility with existing RAN operations. He mentions that the Mogami class can operate as a “mother ship” for unmanned underwater and surface vehicles, aligning with the changing character of naval warfare and the RAN’s decision to develop optionally crewed vessels. In addition, he mentions the Mogami class could bring significant savings in maintenance, sustainment and future enhancements through economies of scale. However, Dan points out that Japan does not have a strong defense export record, meaning it may still have to build complex relationships and processes to export the Mogami class frigate. Building Mogami class frigates in Australia would raise several challenges, including funding gaps and workforce limitations. Delays and budget overruns have marked past Australian shipbuilding projects. Dan Darling notes that there’s uncertainty about whether will receive the necessary funding, with much of the estimated \$35 billion envisioned still unfunded. Darling mentions that Australia’s shipbuilding workforce is limited, raising concerns about meeting delivery deadlines and maintaining enough skilled labor to avoid the so called “Valley of Death” — a cycle of boom and bust in orders that affects worker retention and industrial know how. He says past defense projects have experienced delays and budget overruns, and there’s worry that these issues could recur, affecting the timely delivery of new ships. In line with that, Darling says that as Australia retires older ships and new ships face delays, there’s a concern about the immediate reduction in warship capacity before the long term expansion takes effect. Despite that, he says the Australian government aims to avoid the mistakes of previous big ticket defense projects, which have suffered from various production and post delivery problems. Darling also notes the need for skilled personnel to operate hi tech platforms and use other emerging technologies. In addition, he says that Australia’s shipbuilding program must align with its strategic needs, given China’s People’s Liberation Army – Navy’s (PLA N) rising capabilities. While Japan has maintained a pacifist foreign policy since the end of World War II, the possibility of exporting Mogami class frigates to Australia may be perceived as an abrupt change in this longstanding stance. Highlighting the policy implications of Japan’s possible export of Mogami class frigates, Sato Heigo notes that doesn’t clarify how the transfer of conventional weaponry will contribute to regional deterrence, juxtaposing it with the vague goal of “creating a desirable security environment.” Heigo notes that debates continue on whether to allow military equipment co developed with other countries to be re exported to third countries. Those debates may influence the outcome of other planned Mogami class frigate exports, . Heigo notes that the new policy suggests a more proactive stance on arms exports but emphasizes that Japan must balance this with its pacifist national identity. He also notes that there is domestic opposition on ethical grounds to the possibility of Japanese made weapons being used in conflicts elsewhere.",
                pubDate = "14.05.2024, 08:30",
                sourceUrl = "https://asiatimes.com"
            ),
            onBackButtonClicked = {},
            onBookmarkButtonClicked = {}
        )
    }
}