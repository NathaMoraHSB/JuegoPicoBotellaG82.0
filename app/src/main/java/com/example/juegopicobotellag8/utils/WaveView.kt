import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class WaveView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        color = resources.getColor(android.R.color.white, null)
        style = Paint.Style.FILL
    }
    private val path = Path()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()

        path.reset()
        path.moveTo(0f, height / 2)

        // Coordenadas manuales para ondas irregulares
        // **Primera onda**
        path.cubicTo(
            width * 0.8f, height * 0.8f,    // Primer punto de control: Desplaza la curva hacia abajo (valle)
            width * 0.1f, height * 0.4f,    // Segundo punto de control: Desplaza la curva hacia arriba (cresta)
            width * 0.3f, height * 0.6f     // Punto final: Termina ligeramente hacia abajo (valle suave)
        )

        // **Segunda onda**
        path.cubicTo(
            width * 0.1f, height * 0.6f,    // Primer punto de control: Extiende la curva hacia abajo (valle profundo)
            width * 0.5f, height * 0.2f,    // Segundo punto de control: Eleva la curva hacia arriba (cresta alta)
            width * 0.6f, height * 0.8f     // Punto final: Termina hacia abajo (valle)
        )

        // **Tercera onda**
        path.cubicTo(
            width * 0.7f, height * 0.4f,    // Primer punto de control: Desplaza la curva hacia arriba (cresta)
            width * 0.8f, height * 1.0f,    // Segundo punto de control: Extiende la curva hacia abajo (valle profundo)
            width * 0.9f, height * 0.5f     // Punto final: Termina en un punto medio (transición)
        )

        // **Cuarta onda**
        path.cubicTo(
            width * 1.0f, height * 0.8f,    // Primer punto de control: Desplaza la curva hacia abajo (valle)
            width * 1.1f, height * 0.3f,    // Segundo punto de control: Eleva la curva hacia arriba (cresta alta)
            width, height / 2               // Punto final: Termina en el borde derecho de la vista, a la mitad de la altura
        )

        // **Cierre del contorno**
        path.lineTo(width, height) // Traza una línea desde el último punto hasta la esquina inferior derecha.
        path.lineTo(0f, height)    // Traza una línea desde la esquina inferior derecha hasta la esquina inferior izquierda.
        path.close()               // Cierra la forma.

        // **Dibuja el contorno en el lienzo**
        canvas.drawPath(path, paint)
    }



}
