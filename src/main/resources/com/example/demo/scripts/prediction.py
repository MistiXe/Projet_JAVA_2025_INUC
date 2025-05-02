import sys
import json
import random

# Simulation d'une IA de scoring

def predict(data):
    suspects = data.get("suspects", [])
    temoignages = data.get("temoignages", {})

    predictions = []

    for suspect_id in suspects:
        base_score = random.uniform(0.2, 0.5)

        nb_temoins = sum(len(lst) for k, lst in temoignages.items() if str(suspect_id) == k)
        score = base_score + nb_temoins * 0.1
        score = min(score, 0.99)

        predictions.append({
            "id": suspect_id,
            "score": round(score, 2)
        })

    predictions = sorted(predictions, key=lambda s: s["score"], reverse=True)
    return predictions


if __name__ == "__main__":
    if len(sys.argv) < 2:
        print(json.dumps({"error": "Fichier JSON requis"}))
        sys.exit(1)

    filepath = sys.argv[1]
    with open(filepath, "r") as f:
        affaire_data = json.load(f)

    results = predict(affaire_data)

    print(json.dumps({"predictions": results}))
