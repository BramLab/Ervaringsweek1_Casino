# Pay-To-Lose Casino Suite  
*(Java SE 17 — Console / CLI Edition)*  

## Table of Contents
* [Team](#team)
* [Introduction](#introduction)
* [Architecture](#architecture)
* [Technologies](#technologies)
* [Setup](#setup)
* [Improvements](#improvements)
* [Contributing](#contributing)
* [License](#license)
* [Disclaimer](#disclaimer)

---

## Team  

> **Overwrite this table once each member confirms the assignment.**  
> For now we map the four machine-levels to the four developers.  
> Feel free to swap responsibilities before coding starts.

| Level / Module            | Primary Owner | GitHub                                        | Backup Reviewer |
|---------------------------|---------------|-----------------------------------------------|-----------------|
| 1 — ClawMachine (1 €)     | **IRA Mabega**| <https://github.com/Ira-16>                   | Tamara          |
| 2 — SlotMachine (50 €)    | **Bram Labarque** | <https://github.com/BramLab>                | Richard         |
| 3 — Lotto (100 €)         | **Tamara Tomova**| <https://github.com/Tamara060890>           | IRA             |
| 4 — Roulette (200 €)      | **Guy Richard Ibambasi** | <https://github.com/GuyRichardib>      | Bram            |
| Core API + Menu + Player  | *shared*      | —                                             | —               |

> Project started **15 May 2025**.

Repository: <https://github.com/BramLab/Ervaringsweek1_Casino>

---

## Introduction  

A training project where *“the house always wins”*—but the code stays clean and test-driven.  
We implement four deliberately biased casino machines plus a simple user interface that:

1. Greets the user, asks for their initial **budget**  
2. Lets them choose a game from a **menu**  
3. Updates winnings / losses until they leave or go broke  

---

## Architecture  

```text
┌───────────────────────┐
│      <<interface>>    │
│        Casino         │◄─── super-type for every machine
│ +int getPlayCost()    │
│ +int play(int stake)  │
│ +String name()        │
└─────────┬─────────────┘
  «implements»    «implements»
┌──────────────┐  ┌──────────────┐   ┌──────────────┐   ┌──────────────┐
│ ClawMachine  │  │ SlotMachine  │   │  Lotto       │   │  Roulette    │
└──────────────┘  └──────────────┘   └──────────────┘   └──────────────┘

┌───────────────────────┐
│        Player         │
│ -int budget           │
│ +profit(int amount)   │
│ +loss(int amount)     │
└───────────────────────┘

┌───────────────────────┐
│      MainApp          │
│  • displays menu      │
│  • drives game loop   │
└───────────────────────┘
