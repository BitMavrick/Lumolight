package com.bitmavrick.lumolight.ui.screen.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cookie
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SettingsItem(
    title: String,
    subTitle: String,
    leadingIcon: ImageVector,
    showSwitch: Boolean = false,
    switchChecked: Boolean = false,
    onClick: () -> Unit,
) {
    Row (
        Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .height(84.dp)
    ){

        Column(
            Modifier
                .fillMaxHeight()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null
            )
        }

        Column (
            Modifier.fillMaxHeight().weight(1f),
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
                Modifier.fillMaxHeight().padding(16.dp),
                verticalArrangement = Arrangement.Center,
            ){
                Switch(
                    modifier = Modifier.semantics { contentDescription = "Switch" },
                    checked = switchChecked,
                    onCheckedChange = { onClick() }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsItemPreview() {
    SettingsItem(
        title = "Package Type",
        subTitle = "release",
        leadingIcon = Icons.Outlined.Cookie,
        showSwitch = true,
        switchChecked = true,
        onClick = {}
    )
}