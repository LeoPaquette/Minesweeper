# Minesweeper-Java

A console-based implementation of the classic **Minesweeper** game written in Java.  
This project demonstrates object-oriented programming principles such as encapsulation, recursion, input validation, and 2D array manipulation.

---

## Game Features

- Customizable board size (1–10 rows/columns)
- Adjustable number of bombs (max 30 or total-1 cells)
- Smart auto-reveal of adjacent empty cells (flood fill behavior)
- Clear and structured console UI with coordinate-based input
- Replay support and full input validation
- Win/loss detection

---

## Technologies Used

- Java (Standard Edition)
- Console I/O with `Scanner`
- Core Java collections (e.g., `ArrayList`, `Collections`)
- Recursion for auto-revealing cells

---

## File Overview

| File        | Description                                                                         |
|-------------|-------------------------------------------------------------------------------------|   
| `Game.java` | Contains the core game logic: board setup, bomb placement, cell checking, win logic |
| `UI.java`   | Handles user interaction, input validation, board printing, and game loop           |

---

## Example Gameplay

```
Enter number of rows (1-10): 5
Enter number of columns (1-10): 5
Enter number of bombs (1-24): 5

Current Board:
   1 2 3 4 5
1  X X X X X
2  X X X X X
3  X X X X X
4  X X X X X
5  X X X X X

Enter Y coordinate: 2
Enter X coordinate: 3
... (continues) ...
```

---

## About Me

*Leo Paquette*  
*Computer Engineering Technology – Computing Science*  
*041147786*  
*paqu0450@algonquinlive.com*

---

## 📌 Notes

- The board is represented using a 2D char array.
- `'X'` indicates unrevealed cells, `'B'` is a bomb, and numbers (`'1'`–`'8'`) represent adjacent bomb counts.
- The win condition is triggered when all non-bomb cells are revealed.
