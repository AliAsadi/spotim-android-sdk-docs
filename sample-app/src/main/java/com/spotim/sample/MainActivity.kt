package com.spotim.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import spotIm.common.SpotCallback
import spotIm.common.SpotException
import spotIm.common.options.ConversationOptions
import spotIm.common.sort.SortType
import spotIm.sdk.SpotIm

/**
 * Created by Ali Asadi on 13/01/2020
 **/
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO - Use your SpotId
        SpotIm.init(this, "sp_xxx")

        val options = ConversationOptions.Builder()
            .addMaxCountOfPreConversationComments(4)
            .addSortType(SortType.SORT_BEST)
            .build()

        SpotIm.getPreConversationFragment("test", options, object : SpotCallback<Fragment> {
            override fun onSuccess(fragment: Fragment) {
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
            }

            override fun onFailure(exception: SpotException) {
                Log.d("MainActivity", exception.toString())
            }

        })
    }
}
