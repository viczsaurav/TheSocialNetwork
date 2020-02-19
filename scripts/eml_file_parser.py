import eml_parser

class Person:
    def __init__(self,name,email):
        self.name=name
        self.email=email

def json_serial(obj):
    if isinstance(obj, datetime.datetime):
        serial = obj.isoformat()
        return serial

def read_parse(fileName):
    with open(fileName, 'rb') as fhdl:
        raw_email = fhdl.read()
    return eml_parser.eml_parser.decode_email_b(raw_email)

if __name__=='__main__':
    parsed_eml = read_parse('../src/main/resources/sample.eml')
    header = parsed_eml['header']['header']
    ## Create
    from_user = Person(header['x-from'][0], header['from'][0])
    to_email_id = [x.strip() for x in (header['to'][0]).split(",")]
    to_email_name = [x.strip() for x in (header['x-to'][0]).split(",")
                     if (len(x.split("@"))==1 or         ## Only allow String Names
                         (len(x.split("@"))==2 and      ## Only names if email ends with `enron.com`
                          x.split("@")[1]=="enron.com"
                          )
                         )
                     ]
to_users=[]
for i,j in  zip(to_email_name, to_email_id):
    to_users.append(Person(i,j))

print('From User: [{}], To-Users(size): [{}]'.format(from_user.name, len(to_users)))



