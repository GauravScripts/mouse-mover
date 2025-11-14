# Mouse Mover 🖱️

A simple Java application that automatically moves your mouse cursor to prevent your laptop from locking during idle periods. Perfect for users on Microsoft Teams or other applications that lock the screen after a period of inactivity.

## Problem Statement

When using Microsoft Teams or working on a laptop with screen lock enabled after 5 minutes of inactivity, your presence status changes to "Away" or your system locks completely. This application solves that problem by automatically moving your mouse cursor slightly every 5 minutes when no user activity is detected.

## Features

✨ **Key Features:**
- 🖱️ Automatically moves mouse cursor every 5 minutes of inactivity
- ⌨️ Easy to stop with a single keypress (ESC key)
- 🎯 Minimal mouse movement (just 1 pixel movement and back)
- 📊 Detects actual user activity and respects manual mouse movements
- 🖼️ Lightweight GUI with focus requirements

## How It Works

1. **Monitors Mouse Activity**: Continuously tracks the current mouse position
2. **Detects Inactivity**: If the mouse hasn't moved for 5 minutes (300,000 ms), the application automatically moves it
3. **Smart Movement**: Moves the mouse 1 pixel to the right and down, then back to the original position
4. **User Control**: Press the **ESC** key to stop the application at any time
5. **Manual Activity**: If you manually move the mouse, the timer resets

## Demo Video

Watch the application in action:

<video width="600" controls>
  <source src="https://github.com/GauravScripts/mouse-mover/raw/refs/heads/master/video/recording.mp4" type="video/mp4">
  Your browser does not support the video tag.
</video>

## Prerequisites

- Java 8 or higher
- Windows, macOS, or Linux with Java Runtime Environment (JRE)

## Installation & Usage

### Clone the Repository
```bash
git clone https://github.com/gauravscripts/mouse-mover.git
cd mouse-mover
```

### Compile
```bash
javac src/MouseMoveAutomatically.java
```

### Run
```bash
java -cp src MouseMoveAutomatically
```

## Controls

| Key | Action |
|-----|--------|
| **ESC** | Stop the application and exit |

## Configuration

You can modify the inactivity timeout by changing the `300000` value in the code (currently set to 5 minutes):

```java
if (System.currentTimeMillis() - lastMoveTime >= 300000) { // 5 minutes
```

**Time values (in milliseconds):**
- 1 minute = 60000
- 2 minutes = 120000
- 3 minutes = 180000
- 5 minutes = 300000
- 10 minutes = 600000

## Use Cases

- 👥 Microsoft Teams users who need to maintain "Active" status
- 💻 Remote workers with auto-lock enabled
- 📱 Anyone who needs to prevent their system from locking during idle work

## Important Notes

⚠️ **Disclaimer:**
- Use this application responsibly and in accordance with your organization's policies
- Some companies may have security policies that discourage preventing auto-lock
- The application requires focus on the window to detect keyboard input
- Always ensure you have permission from your IT department before using this tool

## Code Structure

- **Main Class**: `MouseMoveAutomatically.java`
  - Manages the mouse movement logic
  - Handles keyboard input (ESC to exit)
  - Runs on a separate thread to avoid blocking the UI

## Contributing

Feel free to fork this project and submit pull requests for improvements!

## License

This project is open source and available for personal use.

## Author

**GitHub**: [@gauravscripts](https://github.com/gauravscripts)

---

**Star this repository** ⭐ if you find it helpful!

