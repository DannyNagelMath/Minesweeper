# Minesweeper

A fully featured Minesweeper clone built with **Java** and **JavaFX**.

![screenshot](docs/readme_pic.png)

## Installation

Download the installer from the [Releases](https://github.com/DannyNagelMath/Minesweeper/releases) page, run it on Windows, and a desktop shortcut is created automatically. No JDK or JavaFX installation required.

## Features

- Three preset difficulty levels: **Beginner** (10×10, 10 mines), **Intermediate** (16×16, 40 mines), and **Expert** (16×30, 99 mines)
- **Custom game mode** — configure any grid size and mine count via an in-app dialog
- Live mine counter and elapsed-time display with classic 7-segment styling
- Smiley-face reset button; sunglasses face on win
- Left-click to reveal, right-click to flag

## Tech Stack

| | |
|---|---|
| Language | Java 17 |
| UI Framework | JavaFX |
| Build & Packaging | Maven · jpackage · launch4j |

## Build from Source

Clone the repository:

```
git clone https://github.com/DannyNagelMath/Minesweeper.git
```

Open the project in IntelliJ IDEA. IntelliJ should automatically detect the `pom.xml` and import it as a Maven project. If prompted, click **Trust Project**.

JavaFX is bundled via Maven dependencies, so **no separate JavaFX installation is required**. However, you do need **JDK 21+** installed.

To run the app:
- **From IntelliJ:** Right-click `Runner.java` → **Run 'Runner.main()'**
- **From Maven:** Run `mvn clean javafx:run` in the terminal

## Project Structure

```
Minesweeper/
├── .mvn/wrapper/          # Maven wrapper support files
├── src/main/java/
│   ├── com/dannynagel/minesweeper/
│   │   ├── Runner.java    # Application entry point; manages scenes and difficulty menu
│   │   ├── GameBoard.java # Grid logic — mine placement, reveal, flood-fill
│   │   ├── Tile.java      # Individual cell state and rendering
│   │   └── Icon.java      # Custom-drawn face/icon graphics
│   └── module-info.java   # Java module declaration
├── docs/                  # Screenshots and documentation assets
├── .gitignore
├── mvnw / mvnw.cmd        # Maven wrapper scripts (Windows and Unix)
└── pom.xml                # Maven build configuration
```

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
