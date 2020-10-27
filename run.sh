currentdir=$(pwd)
javafxDir="/usr/share/java/lib"

java --module-path $javafxDir:$currentdir/lib --add-modules com.jfoenix --add-modules javafx.controls,javafx.media,javafx.base,javafx.fxml,com.jfoenix --add-exports=javafx.graphics/com.sun.javafx.scene=com.jfoenix -jar quinzical.jar
