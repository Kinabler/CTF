for i in $(seq 4999 50);
do
        echo "$i:"
        curl https://directory.web.actf.co/$i.html
        echo \n
done
