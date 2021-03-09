import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chronoclock.utils.format

@Composable
fun CountDownInput(
    modifier: Modifier = Modifier,
    time: String,
    onTimeUpdated: (String) -> Unit
) {
    Row(
        modifier = Modifier.size(150.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        val minutes: Int = time.split(":")[0].trim().toInt()
        val seconds: Int = time.split(":")[1].trim().toInt()

        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
            Icon(
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        onTimeUpdated(
                            getTime(
                                minutes = minutes + 1,
                                seconds = seconds
                            )
                        )
                    },
                imageVector = Icons.Filled.AddCircle,
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )

            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp),
                text = minutes.format(),
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onPrimary
            )

            Icon(
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        onTimeUpdated(
                            getTime(
                                minutes = minutes - 1,
                                seconds = seconds
                            )
                        )
                    },
                imageVector = Icons.Filled.RemoveCircle,
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
        }

        Text(
            text = ":",
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.primary
        )

        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
            Icon(
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        onTimeUpdated(
                            getTime(
                                minutes = minutes,
                                seconds = seconds + 1
                            )
                        )
                    },
                imageVector = Icons.Filled.AddCircle,
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )

            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp),
                text = seconds.format(),
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onPrimary
            )

            Icon(
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        onTimeUpdated(
                            getTime(
                                minutes = minutes,
                                seconds = seconds - 1
                            )
                        )
                    },
                imageVector = Icons.Filled.RemoveCircle,
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
        }
    }
}

private fun getTime(minutes: Int, seconds: Int): String {
    return if (minutes >= 0 && seconds >= 0) "${minutes.format()}:${seconds.format()}"
    else "00:00"
}
