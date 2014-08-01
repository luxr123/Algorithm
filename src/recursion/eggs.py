#!/usr/bin/env python
# -*- coding: utf-8 -*-
__author = "luxury"

'''解法一'''
#f(0,m) = 0 (m>=1)
#f(n,1) = n (n>=1)
#f(n,m) = min{max{f(i-1,m-1), f(n-i,m)}}
#      1<=i<=n
#
import functools

@functools.lru_cache(maxsize=None)
def f(n,m):
    if n == 0: return 0
    if m == 1: return n

    ans = min([max([f(i - 1, m - 1), f(n - i, m)]) for i in range(1, n + 1)]) + 1
    return ans

print(f(100,2))
print(f(200,2))
print(f(100,7))