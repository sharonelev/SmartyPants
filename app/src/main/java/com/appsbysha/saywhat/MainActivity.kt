package com.appsbysha.saywhat


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.appsbysha.saywhat.model.Child
import com.appsbysha.saywhat.model.Line
import com.appsbysha.saywhat.model.LineType
import com.appsbysha.saywhat.model.Saying
import com.appsbysha.saywhat.model.User

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainViewModel: MainViewModel =
            ViewModelProvider(this)[MainViewModel::class.java]
        Log.d("Firebase_TEST","fetchUserData MAIN")

        val line1 = Line(LineType.OTHER_PERSON, "אני אמא שלך ומה אתה שלי?", "אמא")
        val line2 = Line(LineType.MAIN_CHILD, "האהבה שלך")
        val line3 = Line(LineType.MAIN_CHILD, "I know why it is called a parking lot. Because there is a lot of parking")

        val saying1 = Saying(
            date = System.currentTimeMillis(),
            lineList = listOf(line3)
        )
        val saying2 = Saying(
            date = System.currentTimeMillis(),
            lineList = listOf(line1, line2)
        )

        val yoav = Child(
            dob = Utils.getDob("2014_08_15"),
            name = "Yoav"
        )
        yoav.sayings[saying1.id] = saying1
        yoav.sayings[saying2.id] = saying2
        val userSha171 = User()
        userSha171.children[yoav.childId] = yoav

       // mainViewModel.saveUser("sha171", userSha171)
        mainViewModel.fetchUserData("sha171")
        setContent {

           MainView()
        }
    }


}