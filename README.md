# 210_Project

Compiling and running:

    Windows:

        javac -cp .\lib\jsfml.jar\;.\src\Cbuild -d .\src\build\ .\src\*.java
        java -cp .\lib\jsfml.jar;.\src\build; MenuMaker

    Linux(Havent tested but should work):

        javac -cp ./lib/jsfml.jar/:./src/build/ -d ./src/build/ ./src/java/*.java
        java -cp ./lib/jsfml.jar:./src/build: MenuMaker

!!!The run commands are out of date!!!

On Windows the window opens for a second and then closes, no idea if thats supposed to happen or is it just a Windows thing.
