from multiprocessing import Pool
import hashlib

def verify_counts(original, downloaded):
    '''
    Vefifies count between original fileList provided and downloaded list
    (env) ➜  data python3 integrity-checker.py
    Original: 517401
    Downloaded: 517399
    '''
    with open(original, 'r') as orig, open(downloaded, 'r') as dwnl:
        print('Original: {}'.format(len(orig.readlines())))
        print('Downloaded: {}'.format(len(dwnl.readlines())))
        downloaded_list = dwnl.readlines()
    orig.close()
    dwnl.close()

def verify_checksum(filename):
    '''
    Verifies checksum of each file, writes the checksum failed files in separate file
    '''
    base_path = 'files/'
    f_checksum = filename[-32:]
    calculated_checksum = md5Sum(base_path + filename)
    # print('filename: [{}] , given checksum: [{}], calculated checksum: [{}]'.format(filename, f_checksum, calculated_checksum))
    if calculated_checksum!=f_checksum:
        print(base_path + filename)

def md5Sum(fname):
    '''
    Calculate md5checksum of file.
    '''
    hash_md5 = hashlib.md5()
    with open(fname, "rb") as f:
        for chunk in iter(lambda: f.read(8192), b""):
            hash_md5.update(chunk)
    return hash_md5.hexdigest()

if __name__=='__main__':
    '''
     Inputs are:
      - file-list.txt : List of files to download(from Server)
      - downloaded.txt : List of downloaded files.
                        $ ls downloaded-files/ > downloaded.txt
    '''
    original = 'file-list.txt'
    downloaded = 'downloaded.txt'
    verify_counts(original,downloaded)

    with  open(downloaded, 'r') as f:
        for fname in f:
            verify_checksum(fname.rstrip())
    f.close

### Output

# Original: 517401
# Downloaded: 517399
#
# 2 missing files:
# ./files/panus-s/inbox/30. 6957ce3c0c3af075c64d29f8eccbdcfb
# ./files/guzman-m/notes_inbox/1710. 720920bfb7f316073008cf90b1221268
# ---------------------------
# Checksum validation failed for following files:

# filename                                                                        Calculated Checksum
# ./files/corman-s/inbox/4. 0c89880b9b531738c4a66d76137f6cd1			      	=> 0c89880b9b531738b4a66d76137f6cd1
# ./files/parks-j/sent_items/497. 04ab41e84ccb225e7b513a229a7498c1          => 545e3c355bcf0592e4f3d35d7ef66f89
# ./files/scott-s/all_documents/1138. d787ab9ea88d09fe56f63857c7d5f0fc          => 9ec77af291c45f127c40b7f01a65d806
# ./files/skilling-j/sent/63. f357a671076bc46d4775271c1c14d227				=> f357a671086bc46d4775271c1c14d227

