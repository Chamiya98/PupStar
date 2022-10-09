use DogCare

SELECT ClinicID,
		sum(Case when Type=1 then 1 else 0 end) as good,
		sum(Case when Type=0 then 1 else 0 end) as bad,
		sum(Case when Type=2 then 1 else 0 end) as unknown,
		count(*) as all_count
  FROM Comments group by ClinicID







@app.route('/getCinicList_1', methods=['GET', 'POST'], endpoint='cliniclist')
def upload():
    resarr = []
    cursor.execute(
        "SELECT ClinicID,sum(Case when Type=1 then 1 else 0 end) as good,sum(Case when Type=0 then 1 else 0 end) as bad,sum(Case when Type=2 then 1 else 0 end) as unknown,count(*) as all_count FROM Comments group by ClinicID")
    testt = cursor.fetchall()
    # json_output = json.dumps(testt)
    for row in testt:
        resarr.append([x for x in row])  # or simply data.append(list(row))
    return jsonify(
        message=resarr,
    )