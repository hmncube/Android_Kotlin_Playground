package com.hmncube.markdownpoc

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import coil.ImageLoader
import com.hmncube.markdownpoc.ui.theme.MarkdownPOCTheme
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.Markwon
import io.noties.markwon.core.MarkwonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarkdownPOCTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TestMarkDown(applicationContext)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun TestMarkDown(applicationContext: Context) {
    val coilPlugin = CustomCoilImagesPlugin.create(LocalContext.current)

    val markwon = Markwon.builder(applicationContext)
        .usePlugin(coilPlugin)
        .usePlugin(object : AbstractMarkwonPlugin() {
            override fun configureTheme(builder: MarkwonTheme.Builder) {
                builder.headingBreakHeight(0)
                builder.isLinkUnderlined(false)
                builder.linkColor(Color(0xFFF70553).toArgb())
            }
        })
        .build()
    MarkdownText(
        SampleMarkDown,
        markdownConfiguration = markwon
    )
}

@Composable
fun MarkdownText(
    markdown: String,
    modifier: Modifier = Modifier,
    markdownConfiguration: Markwon,
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            TextView(context).apply {
                // You can configure your TextView as you need
            }
        },
        update = { view ->
            // Here, you can do something with 'view' using the 'markdown' and 'markdownConfiguration'
            markdownConfiguration.setMarkdown(view, markdown)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MarkdownPOCTheme {
        Greeting("Android")
    }
}
