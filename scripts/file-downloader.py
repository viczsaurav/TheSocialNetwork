import wget
from multiprocessing import Pool

def download(line):
    base_url = 'http://10.8.0.1/'
    base_path = 'files/'

    url = base_url + line.rstrip().replace('./', '').split(' ')[0]
    dest = base_path + line.rstrip().replace('.','').replace(' ','').replace('/','_')

    try:
        wget.download(url,out=dest)
    except ValueError as error:
        print('\nFile exists file: {}\n{}'.format(url, error))
    except:
        print('\nError while downloading file: {}'.format(url, error))

if __name__=='__main__':
    fileName = 'file-list.txt'
    lines=[]
    with open(fileName, 'r') as f:
        lines = f.readlines()
    f.close()

    ## Multi-threaded
    Pool = Pool(processes=1000)
    Pool.map_async(download, lines)
    Pool.close()
    Pool.join()
    print('----END----')
