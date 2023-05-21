package study.android.kotlindbnotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    val db by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java, "results.db"
        ).build()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val context = this
        GlobalScope.launch {
            for (company in TestData.russianCompanies2020) {
                db.resultsDao().insert(company)
            }
        }

        val companies_list = findViewById<RecyclerView>(R.id.companies_list)
        val statistics = findViewById<Button>(R.id.statistics)

        companies_list.layoutManager = LinearLayoutManager(this)
        db.resultsDao().getAll("RESULT DESC").observe(this
        ) { results -> companies_list.adapter = ResultAdapter(results) }
        statistics.setOnClickListener {
            startActivity(Intent(this, StatActivity::class.java))
        }


        delete.setOnClickListener {
            val query = toDelete.text.toString()
            GlobalScope.launch {
                db.resultsDao().deleteByName("%$query%")
            }
            Toast.makeText(this, "Компании с заданной подстрокой удалены", Toast.LENGTH_SHORT).show()
        }


    }
}