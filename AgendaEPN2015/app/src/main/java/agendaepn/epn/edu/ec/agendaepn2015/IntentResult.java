/*============PROYECTO SEMINARIO IB ANDROID============
=======================================================
INTEGRANTES:            ANDRADE HECTOR
                        MENSÍAS DANIEL
CURSO: SEMINARIO IB - GR1
=======================================================
CLASE: IntentResult

DESCRIPCION: Esta clase pertenece a la funcionalidad xzing la cual nos permite realizar el
escaneo de un codigo qr de una manera sencilla añadiendo estas librerias y haciendo uso de
la aplicacion barcodescaner. Esta clase en especifico es la que me ayuda a scanear cada una
de las filas y columnas del codigo qr captado por nuestro dispositivo
 */
package agendaepn.epn.edu.ec.agendaepn2015;

public class IntentResult {
    private final String contents;
    private final String formatName;
    private final byte[] rawBytes;
    private final Integer orientation;
    private final String errorCorrectionLevel;

    IntentResult() {
        this(null, null, null, null, null);
    }

    IntentResult(String contents,
                 String formatName,
                 byte[] rawBytes,
                 Integer orientation,
                 String errorCorrectionLevel) {
        this.contents = contents;
        this.formatName = formatName;
        this.rawBytes = rawBytes;
        this.orientation = orientation;
        this.errorCorrectionLevel = errorCorrectionLevel;
    }

    public String getContents() {
        return contents;
    }

    public String getFormatName() {
        return formatName;
    }

    public byte[] getRawBytes() {
        return rawBytes;
    }

    public Integer getOrientation() {
        return orientation;
    }

    public String getErrorCorrectionLevel() {
        return errorCorrectionLevel;
    }

    @Override
    public String toString() {
        StringBuilder dialogText = new StringBuilder(100);
        dialogText.append("Format: ").append(formatName).append('\n');
        dialogText.append("Contents: ").append(contents).append('\n');
        int rawBytesLength = rawBytes == null ? 0 : rawBytes.length;
        dialogText.append("Raw bytes: (").append(rawBytesLength).append(" bytes)\n");
        dialogText.append("Orientation: ").append(orientation).append('\n');
        dialogText.append("EC level: ").append(errorCorrectionLevel).append('\n');
        return dialogText.toString();
    }
}
