/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.ui.screen.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Cookie
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreenTopBar(
    title: String,
    scrollBehavior : TopAppBarScrollBehavior,
    onClickBack: () -> Unit,
    backIconDescription: String
) {
    LargeTopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        navigationIcon = {
            IconButton(onClick = { onClickBack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = backIconDescription
                )
            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
fun SettingSubDividerText(subtitle: String) {
    Row(
        Modifier.padding(horizontal = 16.dp)
    ){
        Text(
            text = subtitle,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun SettingsItem(
    title: String,
    subTitle: String,
    leadingIcon: ImageVector,
    iconDescription: String,
    showSwitch: Boolean = false,
    switchChecked: Boolean = false,
    thumbIcon: ImageVector = Icons.Filled.Check,
    onClick: () -> Unit,
) {
    Row (
        Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .height(92.dp)
    ){

        Column(
            Modifier
                .fillMaxHeight()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = iconDescription
            )
        }

        Column (
            Modifier
                .fillMaxHeight()
                .weight(1f).padding(end = 16.dp),
            verticalArrangement = Arrangement.Center
        ){
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = subTitle,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        if(showSwitch){
            Column(
                Modifier
                    .fillMaxHeight()
                    .padding(end = 16.dp),
                verticalArrangement = Arrangement.Center,
            ){
                Switch(
                    checked = switchChecked,
                    onCheckedChange = { onClick() },
                    thumbContent = if (switchChecked) {
                        {
                            Icon(
                                imageVector = thumbIcon,
                                contentDescription = "option enabled",
                                modifier = Modifier.size(SwitchDefaults.IconSize),
                            )
                        }
                    } else {
                        null
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun SettingScreenTopBarPreview(){
    SettingScreenTopBar(
        title = "Settings",
        scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(),
        onClickBack = {},
        backIconDescription = "Going Back"
    )
}

@Preview(showBackground = true)
@Composable
fun SettingsItemPreview() {
    SettingsItem(
        title = "Package Type",
        subTitle = "release",
        leadingIcon = Icons.Outlined.Cookie,
        iconDescription = "Settings Icon",
        showSwitch = true,
        switchChecked = true,
        onClick = {}
    )
}