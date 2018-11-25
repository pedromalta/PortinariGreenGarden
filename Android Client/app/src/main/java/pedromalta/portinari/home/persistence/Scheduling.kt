package pedromalta.portinari.home.persistence

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

class Scheduling: RealmObject() {
    @PrimaryKey
    var start: Date = Date()
    var time: Int = 0
    var sprinkler1Done: Boolean = false
    var sprinkler2Done: Boolean = false

    var isDone
            get {}
}