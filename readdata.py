import pandas as pd

df = pd.read_csv('data.csv')

df.to_json('dados.json', orient='records')
