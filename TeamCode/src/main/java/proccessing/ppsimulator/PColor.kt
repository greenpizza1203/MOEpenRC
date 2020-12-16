package proccessing.ppsimulator
//
class PColor(val r: Int, val g: Int, val b: Int) {

    companion object {
        fun red(value: Int) = rgb(value, 0, 0)

        private fun rgb(r: Int, g: Int, b: Int): PColor {
            return PColor(r,g,b)
        }
    }
}