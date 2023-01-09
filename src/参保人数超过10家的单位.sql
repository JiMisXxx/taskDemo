select
    (CASE WHEN insuno in ('440300','440399') THEN '深圳市市本级'
          WHEN insuno = '440303' THEN '罗湖区'
          WHEN insuno = '440304' THEN '福田区'
          WHEN insuno = '440305' THEN '南山区'
          WHEN insuno = '440306' THEN '宝安区'
          WHEN insuno = '440307' THEN '龙岗区'
          WHEN insuno = '440308' THEN '盐田区'
          WHEN insuno in ('440309','440342') THEN '龙华区'
          WHEN insuno = '440310' THEN '坪山区'
          WHEN insuno = '440311' THEN '光明区'
          WHEN insuno = '440343' THEN '大鹏新区'
          ELSE insuno
        end),
    count(distinct emp_no)
from
    (select
         emp_no,substr(optins_no,1,6) insuno
     from
         (select
              c.optins_no,c.emp_no,row_number() over ( partition by c.psn_no order by c.cashym desc) num
          from
              cep_clctcent_db.stg_staf_psn_clct_detl_d_di c
          where
                  c.biz_date>='2021-05-01'
            and c.tx_area_code in ('440000','440300')
            and c.cashym >= '202211'
            and c.poolarea_no like '4403%' )a
     where
             num = 1
     group by
         emp_no,substr(optins_no,1,6)
     having count(emp_no) > 10)a
group by
    (CASE WHEN insuno in ('440300','440399') THEN '深圳市市本级'
          WHEN insuno = '440303' THEN '罗湖区'
          WHEN insuno = '440304' THEN '福田区'
          WHEN insuno = '440305' THEN '南山区'
          WHEN insuno = '440306' THEN '宝安区'
          WHEN insuno = '440307' THEN '龙岗区'
          WHEN insuno = '440308' THEN '盐田区'
          WHEN insuno in ('440309','440342') THEN '龙华区'
          WHEN insuno = '440310' THEN '坪山区'
          WHEN insuno = '440311' THEN '光明区'
          WHEN insuno = '440343' THEN '大鹏新区'
          ELSE insuno
        end)
    limit 100;

福田区	23611
深圳市市本级	8605
罗湖区	10855
大鹏新区	978
宝安区	35865
光明区	19445
南山区	23517
坪山区	4071
盐田区	2603
龙华区	372
龙岗区	29657
