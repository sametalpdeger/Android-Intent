package org.example.intent

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : ComponentActivity() {
    val uri = MutableStateFlow<String?>(null)

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val uri = intent.data

        if (uri != null) {
            val uriString: String = "URI: $uri"
            println(uriString)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            MaterialTheme {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val uriString = uri.collectAsStateWithLifecycle()

                    if (uriString.value != null) {
                        Text(text = uriString.value!!)
                    }

                    Text(
                        "Explicit intents specify which component of which application will satisfy the intent, by specifying a full ComponentName. You'll typically use an explicit intent to start a component in your own app, because you know the class name of the activity or service you want to start. For example, you might start a new activity within your app in response to a user action, or start a service to download a file in the background.\n" +
                                "Implicit intents do not name a specific component, but instead declare a general action to perform, which allows a component from another app to handle it. For example, if you want to show the user a location on a map, you can use an implicit intent to request that another capable app show a specified location on a map.\n",
                        fontSize = 10.sp,
                        lineHeight = 15.sp,
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        "Explicit Intents",
                        fontSize = 20.sp
                    )
                    Button(
                        onClick = {
                            Intent(applicationContext, MainActivity2::class.java).also {
                                startActivity(it)
                            }
                        }
                    ) {
                        Text(text = "Go to MainActivity2", color = Color.White)
                    }


                    Button(
                        onClick = {
                            Intent(Intent.ACTION_MAIN).also {
                                it.`package` = "com.android.chrome"

                                try {
                                    startActivity(it)
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF0F9D58),
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Open chrome", color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        "Implicit Intents",
                        fontSize = 20.sp
                    )

                    Button(
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xff9d0f3a),
                            contentColor = Color.White
                        ),
                        onClick = {
                            try {
                                startActivity(Intent(Intent.ACTION_SEND).also {
                                    it.type = "text/plain"
                                    it.putExtra(Intent.EXTRA_TEXT, "Hello World")
                                    it.putExtra(Intent.EXTRA_SUBJECT, "Important subject")
                                    it.putExtra(Intent.EXTRA_EMAIL, arrayOf("example@example.com"))

                                })
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    ) {
                        Text(text = "Send Email", color = Color.White)
                    }

                }
            }
        }

    }
}
