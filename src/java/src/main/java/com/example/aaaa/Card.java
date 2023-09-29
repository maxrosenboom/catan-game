package com.example.aaaa;

public interface Card {
    int getTypeId();
    // 1 to 5 are development cards (1: knight, 2: road building, 3: year of plenty, 4: monopoly, 5: victory points)
    // 6 to 10 are resource cards (6: grain, 7: wool, 8: lumber, 9: brick, 10: ore)
    String getName(); //format: <type>: <name>, example: "Resource Card: Wool"
}
