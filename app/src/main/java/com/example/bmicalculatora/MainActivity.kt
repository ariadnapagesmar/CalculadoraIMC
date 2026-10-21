package com.example.bmicalculatora

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.SeekBar

class MainActivity : AppCompatActivity() {
    var alsada: Int = 180
    var pes: Int = 0
    var nom: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var btnMes: Button = findViewById(R.id.btn_mes)
        var btnMenys: Button = findViewById(R.id.btn_menys)
        var tvPes: TextView = findViewById(R.id.tv_num_pes)

        btnMes.setOnClickListener {
            pes += 1
            tvPes.text = ""+pes+" kg"
        }
        btnMenys.setOnClickListener {
            pes -= 1
            tvPes.text = ""+pes+" kg"
        }

        var bar_alsada: SeekBar = findViewById(R.id.bar_alsada)
        var tvAlsada: TextView = findViewById(R.id.tv_num_alsada)

        // Associem un "listener" a la barra: és un objecte que s'encarrega
        // de reaccionar quan l'usuari interactua amb la barra (bar_alsada).
        bar_alsada.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            // S'executa CADA VEGADA que el valor de la barra canvia,
            // és a dir, mentre l'usuari l'arrossega.
            // - sb: la barra que s'ha mogut
            // - progress: el valor actual (de 0 fins al màxim, 300)
            // - fromUser: true si l'ha mogut l'usuari, false si s'ha canviat des del codi
            override fun onProgressChanged(sb: SeekBar?, progress: Int, fromUser: Boolean) {
                // Convertim el número a text i el mostrem al TextView,
                // perquè el valor es vagi actualitzant a la pantalla.
                tvAlsada.text = progress.toString() + " cm"
            }

            // S'executa UNA vegada, quan l'usuari toca la barra per començar a arrossegar.
            // Aquí no necessitem fer res, però cal definir-la perquè és obligatòria.
            override fun onStartTrackingTouch(sb: SeekBar?) {}

            // S'executa UNA vegada, quan l'usuari deixa anar la barra.
            // Tampoc la necessitem, però també és obligatòria.
            override fun onStopTrackingTouch(sb: SeekBar?) {}
        })

        var etNom: EditText = findViewById(R.id.et_nom)
        var btnCalcular: Button = findViewById(R.id.btn_calcular)

        var tvResultatNom: TextView = findViewById(R.id.tv_resultat_nom)
        var tvResultatCalcul: TextView = findViewById(R.id.tv_resultat_calcul)
        var tvResultatPes: TextView = findViewById(R.id.tv_resultat_pes)

        btnCalcular.setOnClickListener {

            // Agafem el text que l'usuari ha escrit a la caixa del nom.
            // .text dona un tipus especial, per això fem .toString() per tenir un text normal.
            val nomUsuari = etNom.text.toString()

            // La barra dona l'alçada en centímetres (per exemple 180),
            // però la fórmula de l'IMC necessita metres (1.80), així que dividim entre 100.
            // Fem servir 100.0 (amb decimal) perquè el resultat també tingui decimals.
            val alsadaMetres = bar_alsada.progress / 100.0

            // Comprovem que l'usuari hagi posat alçada i pes.
            // Si no, no podem calcular res (i dividir entre 0 donaria error).
            if (alsadaMetres == 0.0 || pes <= 0) {

                tvResultatNom.text = "IMC de " + nomUsuari
                tvResultatCalcul.text = ""
                tvResultatPes.text = "Posa un pes i una alçada primer"

            } else {

                // Fórmula de l'IMC: pes (kg) dividit entre l'alçada (m) al quadrat.
                val imc = pes / (alsadaMetres * alsadaMetres)

                // Aquí ajuntem el text "IMC de " amb el nom que ha escrit l'usuari.
                // El + serveix per unir textos.
                tvResultatNom.text = "IMC de " + nomUsuari

                // Mostrem el resultat numèric. "%.1f" vol dir "només 1 decimal".
                tvResultatCalcul.text = "%.1f".format(imc)

                // Segons el valor de l'IMC, decidim quin missatge mostrar.
                // Si l'IMC és menor que 18.5 -> pes inferior.
                // Si és menor que 25 (i no és menor que 18.5) -> pes saludable.
                // En qualsevol altre cas -> pes superior.
                if (imc < 18.5) {
                    tvResultatPes.text = "Pes inferior al saludable"
                } else if (imc < 25) {
                    tvResultatPes.text = "Pes saludable"
                } else {
                    tvResultatPes.text = "Pes superior al saludable"
                }
            }
        }
    }
}