package com.zinab.veloura2.ui.Screens.profile
import androidx.compose.foundation.border
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    trailing: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp) // المسافة بين العناصر
            .border(
                width = 1.dp,
                color = Color.Gray.copy(alpha = 0.3f), // لون الستروك خفيف
                shape = RoundedCornerShape(12.dp) // زاوايا مدورة
            )
            .padding(horizontal = 16.dp, vertical = 14.dp), // padding داخل المستطيل
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = Color(0xFFE76F51))
        Spacer(modifier = Modifier.width(12.dp))
        Text(title, color = Color.White, modifier = Modifier.weight(1f))
        trailing ?: Icon(Icons.Default.KeyboardArrowRight, null, tint = Color.Gray)
    }



}


@Preview(showBackground = true, backgroundColor = 0xFF1B1A16)
@Composable
fun SettingsSectionPreview() {
    Column {
        SettingsItem(Icons.Default.Person, "Personal Info")
        SettingsItem(Icons.Default.Payment, "Payment Methods")
        SettingsItem(Icons.Default.LocationOn, "Addresses")
        SettingsItem(
            icon = Icons.Default.DarkMode,
            title = "Dark Mode",
            trailing = {
                Switch(checked = true, onCheckedChange = {})
            }
        )
    }
}
