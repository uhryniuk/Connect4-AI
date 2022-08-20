# Use
- Hooks are a middle layer between application specfiic logic and the application iteself.
- Hooks abstract away the implementation details (the how), and just gets the information you want.
- Examples from space-rover:
    - "useGameModes"
        - All frontend cares about is this returns all the gamemodes.
            - It doesn't care how it gets the list of them.
        - But in reality, useGameMode makes HTTP request. the idea is that it is modular, it could get them from anywhere to provide them to the frontend in a realiable format.

- Example of possible hooks for connect4 could be:
    - useAiModes
        - This could return list of all available modes.
    - useAiResponse
        - This can just get a response based on the currently selected AI.