# SnakeFX
This is a clone of the old mobile phone game "Snake".
It was created as an example project to learn *JavaFX 2*.

The object of the game is simple: You are controlling a snake that likes to eat
food. Every time the snake eats some food it grows a little bit. You may not bite yourself in the tail or you are game over. There are no walls in this game so when you move outside of one side of the screen you appear again on the other side of the screen.


*Please note:* You need JavaFX 2.2 installed on your computer to play the game.
It is bundled in the Oracle Java SE since version 1.7.07. If you have installed this version or above there should be no problem with running the game.

As JavaFX 2 is not fully open sourced at the moment, I can't put the needed jar file into the build process. I will fix that when JavaFX 2 is released to OpenSource. According to Oracle this will be the case in februrar 2013. 


### Build the game
The project is based on the build system [maven](http://maven.apache.org/).

As JavaFX 2 is not fully open sourced at the moment, it is not contained in the central maven repository. For this reason it downloaded by maven like normal libraries. To workaround this issue you need to install JavaFX 2.2 on your local machine. 

After that you need to define the maven property `${jfxrt.dir}` in your local maven `settings.xml` file. This file is located at `HOME/.m2/settings.xml`.

This property has to point to the directory where the JavaFX 2 lib `jfxrt.jar` is located. This is typically `JAVA_HOME/jre/lib`.

For example this is my settings.xml file:

    <settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
        http://maven.apache.org/xsd/settings-1.0.0.xsd">
        <profiles>
            <profile>
                <id>javafx</id>
                <activation>
                    <activeByDefault>true</activeByDefault>
                </activation>
                <properties>
                    <jfxrt.dir>/home/manuel/dev/jdk1.7.0_07/jre/lib</jfxrt.dir>
                </properties>
            </profile>
        </profiles>
    </settings>



To build the project simply run `mvn clean install`. After this run `mvn assembly:single` to create the executable jar file and package it into a `zip` file located in the `target` folder. 

To play the game, unpack this zip file to any location and run the jar file inside the extracted folder (`java -jar snakefx.jar`).

