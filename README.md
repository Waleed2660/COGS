# C.O.G.S

- Run "buildme.sh" and "runme.sh" to compile & run the game.

Compiling and running:

    Windows:

        javac -cp .\lib\jsfml.jar\;.\src\build -d .\src\build\ .\src\java\*.java
        java -cp .\lib\jsfml.jar;.\src\build MainMenu

    Linux:

        javac -cp ./lib/jsfml.jar/:./src/build/ -d ./src/build/ ./src/java/*.java
        java -cp ./lib/jsfml.jar:./src/build MainMenu

