import subprocess
from flask import Flask, request
app = Flask(__name__)

@app.route('/')
def index():
	return "Hello!\n visit http://127.0.0.1:5000/data?startloc=0&starttime=12&endtime=18"

@app.route('/data')
def get_data():

	startloc = request.args.get('startloc')
	starttime = request.args.get('starttime')
	endtime = request.args.get('endtime')

	response = subprocess.check_output(['java', 'RT_Design', startloc,  starttime, endtime],universal_newlines=True)
	# print(response)
	response = response.split('exit')[0]
	response = response.split('->')
	# response = list(set(response))#remove duplicates
	result = []
	for i in response:
		present=False
		for j in result:
			if i == j:
				present=True
		if not present:
			result.append(i)
	result = ', '.join(result)
	return result
	# return {"a":23,"b":45,"c":[3,3]}