import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chronoclock.MainViewModel

@Composable
fun CountDownInput(
    modifier: Modifier = Modifier,
    isPlaying: Boolean,
    optionSelected: (MainViewModel.Actions) -> Unit
) {
    Row(
        modifier = Modifier.size(150.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        LazyColumn(modifier = modifier.weight(0.4f)) {
            items(IntArray(60) { it + 1 }.toList()) { index ->
                Text(
                    text = "$index",
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h4
                )
                Divider()
            }
        }
        Text(text = ":", color = MaterialTheme.colors.secondary)

        LazyColumn(modifier = modifier.weight(0.4f)) {
            items(IntArray(60) { it + 1 }.toList()) { index ->
                Text(
                    text = "$index",
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h4
                )
                Divider()
            }
        }
    }
}