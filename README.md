# VexMine

VexMine is a Paper 1.21.11 mining extension for the existing VexBot setup.

Command format:
- /vexbot vexmine mine individual <x1> <y1> <z1> <x2> <y2> <z2>
- /vexbot vexmine mine team <x1> <y1> <z1> <x2> <y2> <z2>

Example:
- /vexbot vexmine mine individual 100 64 200 200 90 300
- /vexbot vexmine mine team 100 64 200 200 90 300

Features:
- creates a mining task for a region
- supports team or individual mode
- automatically mines blocks in the selected volume
- tracks task creation and task progress

Build:
```bash
mvn clean package
```

Install:
- copy the generated JAR from target/ into your Paper server plugins directory
