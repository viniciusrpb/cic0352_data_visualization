import pandas as pd

df = pd.read_csv('data.csv')

df.to_json('dados2.json', orient='records')
