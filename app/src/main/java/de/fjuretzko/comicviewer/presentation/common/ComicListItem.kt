package de.fjuretzko.comicviewer.presentation.common


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun ComicListItem(
    comicTitle: String,
    onItemClick: () -> Unit,
    actions: @Composable (() -> Unit)? = null
) {
    Box(
        modifier = Modifier


    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(2.dp)),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClick() }
                .padding(20.dp)
            ) {
                Text(
                    text = comicTitle,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.align(Alignment.Start)
                )
                Row(horizontalArrangement = Arrangement.End) {
                    if (actions != null) {
                        actions()
                    }
                }

            }
        }
    }
}