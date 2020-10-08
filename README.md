Project Quinzical
============================
## Getting Started
To run the runable jar, use bash to run the following command
```
chmod +x run.sh
./run.sh
```
The project assume `SQLiteQuinzical.db` is in the same directory, if the file is not presenet, try adding `Quinzical.txt`, 
a question set to initialize the database to the same directory.

It would be benifitial to have the following structure when running.

    .
    ├── src                     
    ├── quinzical.jar
    ├── run.sh                  
    └── SQLiteQuinzical.db

If issue still occure please try the adding vm argument to run configuration in below. 

This program also assuem festival and espeak are installed and is callable by bash.
### Scripts
Run script to run the applciation with ease
`run.sh`

Run configuration
```
--module-path /home/se2062020/javafx-sdk-11.0.2/lib  --add-modules javafx.controls,javafx.media,javafx.base,javafx.fxml
--module-path {PROJECT_DIR}/lib --add-modules com.jfoenix --add-exports=javafx.graphics/com.sun.javafx.scene=com.jfoenix
```

### Runtime crashes
Try
- removeing `SQLiteQuinzical.db`, restart the application with `Quinzical.txt` in the same directory
- Disable `text to speech` using the drawer on the right by clicking the button on top right in any menu other than main menu



### Compiled files
`SQLiteQuinzical.db`

### 3rd party libraries
jfoenix-9.0.10.jar
sqlite-jdbc-3.32.3.2.jar

...


### Resource 
All image is retrived from https://unsplash.com/ where it is free for commercial and non-commercial purposes with no permission needed.

### License information
MIT License

Copyright (c) 2020 Neville Loh, Daniel Cutfield

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
