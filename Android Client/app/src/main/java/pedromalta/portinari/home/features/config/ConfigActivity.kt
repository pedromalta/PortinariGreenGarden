package pedromalta.portinari.home.features.config

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import pedromalta.portinari.home.R
import pedromalta.portinari.home.features.base.BaseActivity

class ConfigActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracoes)

        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.add(R.id.preferences, ConfiguracoesFragment())
        transaction.commit()

    }

    override fun onStop() {
        super.onStop()
        android.os.Process.killProcess(android.os.Process.myPid())
    }

    class ConfiguracoesFragment : PreferenceFragmentCompat() {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            // Load the preferences from an XML resource
            setPreferencesFromResource(R.xml.admin, rootKey)
        }


    }

}