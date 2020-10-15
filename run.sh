currentdir=$(pwd)
javafxDir="/home/se2062020/javafx-sdk-11.0.2/lib"

java --module-path $javafxDir:$currentdir/lib --add-modules com.jfoenix --add-modules javafx.controls,javafx.media,javafx.base,javafx.fxml,com.jfoenix --add-exports=javafx.graphics/com.sun.javafx.scene=com.jfoenix -jar quinzical.jar
