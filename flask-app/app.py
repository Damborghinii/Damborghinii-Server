from flask import Flask, request, jsonify
import joblib
import numpy as np

app = Flask(__name__)

model = joblib.load("royalty_model_tuned.pkl")

@app.route("/")
def home():
    return "🎵 음악 저작권 예측 API 서버가 실행 중입니다."

@app.route("/predict", methods=["POST"])
def predict():
    input_data = request.json
    X = np.array([[
        np.log1p(input_data["stream_count"]),
        np.log1p(input_data["sns_mentions"]),
        input_data["search_trend_score"],
        input_data["music_metadata_score"],
        input_data["chart_rank_avg"],
        input_data["is_major_distributor"],
        np.log1p(input_data["artist_follower_count"])
    ]])
    prediction = model.predict(X)
    return jsonify({"predicted_royalty": float(prediction[0])})

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)