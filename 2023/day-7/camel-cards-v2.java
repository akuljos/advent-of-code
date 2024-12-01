import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class CamelCardsV2 {

    static String getHandValue(List<Integer> cardValues) {
        Map<Integer, Integer> cardCounts = new HashMap<Integer, Integer>();

        int jokerCount = 0;

        for (Integer card : cardValues) {
            if (card == 1) {
                jokerCount += 1;
            } else {
                if (cardCounts.containsKey(card)) {
                    cardCounts.put(card, cardCounts.get(card) + 1);
                } else {
                    cardCounts.put(card, 1);
                }
            }
        }

        int highestValue = 0;
        int numPairs = 0;
        for (Integer card : cardCounts.keySet()) {
            int numCards = cardCounts.get(card);
            if (numCards > highestValue) {
                highestValue = numCards;
            }

            if (numCards == 2) {
                numPairs += 1;
            }
        }

        highestValue += jokerCount;

        switch (highestValue) {
            case 5:
                return "five kind";
            case 4:
                return "four kind";
            case 3:
                if ((numPairs == 1 && jokerCount == 0) || (numPairs == 2 && jokerCount == 1)) {
                    return "full house";
                }
                return "three kind";
            case 2:
                if (numPairs == 2) {
                    return "two pair";
                }
                return "one pair";
            default:
                return "high card";
        }
    }

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java CamelCardsV2 [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            Comparator<List<Integer>> handComp = new Comparator<List<Integer>>() {
                public int compare(List<Integer> t1, List<Integer> t2) {
                    
                    for (int i = 0; i < t1.size(); i++) {
                        int c = t1.get(i).compareTo(t2.get(i));

                        if (c != 0) {
                            return c;
                        }
                    }

                    return 0;
                }
            };

            String [] handTypes = {"high card", "one pair", "two pair", "three kind", "full house", "four kind", "five kind"};

            Map<String, ArrayList<List<Integer>>> handMap = new HashMap<String, ArrayList<List<Integer>>>();
            Map<List<Integer>, Long> bidMap = new HashMap<List<Integer>, Long>();

            for (String handType : handTypes) {
                handMap.put(handType, new ArrayList<List<Integer>>());
            }

            while (s.hasNextLine()) {
                String [] line = s.nextLine().split(" ");
                String hand = line[0];
                String bid = line[1];

                List<Integer> cardValues = new ArrayList<Integer>();

                for (char card : hand.toCharArray()) {
                    int cardVal = 0;
                    switch(card) {
                        case 'A':
                            cardVal = 14;
                            break;
                        case 'K':
                            cardVal = 13;
                            break;
                        case 'Q':
                            cardVal = 12;
                            break;
                        case 'J':
                            cardVal = 1;
                            break;
                        case 'T':
                            cardVal = 10;
                            break;
                        default:
                            cardVal = card - '0';
                    }

                    cardValues.add(cardVal);
                }

                bidMap.put(cardValues, Long.parseLong(bid));
                handMap.get(getHandValue(cardValues)).add(cardValues);
            }

            for (String handVal : handTypes) {
                handMap.get(handVal).sort(handComp);
            }

            int sum = 0;
            int rank = 1;

            for (String handVal : handTypes) {
                ArrayList<List<Integer>> currHands = handMap.get(handVal);

                for (List<Integer> currHand : currHands) {
                    sum += (rank * bidMap.get(currHand));
                    rank += 1;
                }
            }

            System.out.println("Total winnings are " + sum);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }


    }
}